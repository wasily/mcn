package me.local.mcn.repositories;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.model.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SubscriptionRepositoryCustomImpl implements SubscriptionRepositoryCustom {
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(SubscriptionRepositoryCustomImpl.class);

    @Override
    public void updateLastSearchTime(String id, LocalDateTime newLocalDateTime) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("lastUpdateTime", newLocalDateTime);
        mongoTemplate.updateFirst(query, update, Subscription.class);
        logger.info("Last search time for subscription {} updated to {}", id, newLocalDateTime);
    }
}
