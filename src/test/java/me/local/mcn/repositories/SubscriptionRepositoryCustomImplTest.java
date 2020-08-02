package me.local.mcn.repositories;

import me.local.mcn.domain.model.Subscription;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubscriptionRepositoryCustomImplTest {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    void updateLastSearchTime() {
        var id = new ObjectId().toString();
        var subscription1 = new Subscription(new ObjectId().toString(), "title1", "email1", LocalDateTime.of(2020, 7, 7, 12, 33));
        var subscription2 = new Subscription(new ObjectId().toString(), "title2", "email2", LocalDateTime.of(2020, 8, 1, 3, 0));
        var subscription3 = new Subscription(id, "title1", "email3", LocalDateTime.of(2020, 5, 7, 21, 23));
        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);
        subscriptionRepository.save(subscription3);
        LocalDateTime newTime = LocalDateTime.of(2020, 8, 2, 20, 0);
        subscriptionRepository.updateLastSearchTime(id, newTime);
        assertTrue(newTime.isEqual(subscriptionRepository.findById(id).get().getLastUpdateTime()));
    }
}