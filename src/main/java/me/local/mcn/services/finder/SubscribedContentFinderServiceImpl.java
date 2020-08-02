package me.local.mcn.services.finder;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.Credentials;
import me.local.mcn.domain.Message;
import me.local.mcn.domain.model.Release;
import me.local.mcn.domain.model.Subscription;
import me.local.mcn.repositories.SubscriptionRepository;
import me.local.mcn.services.notification.NotificationService;
import me.local.mcn.services.release.ReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscribedContentFinderServiceImpl implements SubscribedContentFinderService {
    private final ReleaseService releaseService;
    private final SubscriptionRepository subscriptionRepository;
    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(SubscribedContentFinderServiceImpl.class);
    private final String messageTopic = "New releases found";

    private List<Release> getNewReleases(String title, LocalDateTime time) {
        return releaseService.getReleasesByTime(title, time);
    }

    @Override
    public void notifyAboutNewReleases() {
        LocalDateTime newTime = LocalDateTime.now();
        Map<String, List<Subscription>> subscriptionsMap = subscriptionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Subscription::getEmail, Collectors.toList()));
        subscriptionsMap.forEach((key, value) -> {
            Credentials credentials = new Credentials(key);
            List<Release> releases = value.stream()
                    .map(subscription -> getNewReleases(subscription.getTitle(), subscription.getLastUpdateTime()))
                    .flatMap(Collection::stream).collect(Collectors.toList());
            String messageText = releases.stream().map(release -> release.getTitle() + " " + release.getInfoHash())
                    .collect(Collectors.joining("\r\n"));
            Message message = new Message(messageTopic, messageText);
            notificationService.notify(credentials, message);
        });
        subscriptionsMap.values().stream().flatMap(Collection::stream)
                .forEach(subscription -> subscriptionRepository.updateLastSearchTime(subscription.getId(), newTime));
    }
}
