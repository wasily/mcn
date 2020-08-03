package me.local.mcn.schedule;

import lombok.RequiredArgsConstructor;
import me.local.mcn.services.collector.CollectorService;
import me.local.mcn.services.finder.SubscribedContentFinderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledCollector {
    private final List<CollectorService> collectorServices;
    private final SubscribedContentFinderService subscribedContentFinderService;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    void collect() {
        collectorServices.forEach(CollectorService::collect);
        subscribedContentFinderService.notifyAboutNewReleases();
    }

}
