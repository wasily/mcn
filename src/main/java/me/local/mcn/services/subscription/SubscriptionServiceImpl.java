package me.local.mcn.services.subscription;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.model.Subscription;
import me.local.mcn.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription addSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
}
