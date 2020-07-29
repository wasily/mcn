package me.local.mcn.services.subscription;

import me.local.mcn.domain.model.Subscription;
import me.local.mcn.repositories.SubscriptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    void setUp() {
        subscriptionService = new SubscriptionServiceImpl(subscriptionRepository);
    }

    @Test
    void shouldCallSaveMethodWhenAddingSubscription() {
        var subscription = new Subscription();
        subscription.setTitle("test");
        subscription.setEmail("test@email.test");
        subscription.setLastUpdateTime(LocalDateTime.now());
        when(subscriptionRepository.save(any())).thenReturn(subscription);
        Assertions.assertThat(subscription).isEqualToComparingFieldByField(subscriptionService.addSubscription(new Subscription()));
    }
}