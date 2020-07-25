package me.local.mcn.repositories;

import me.local.mcn.domain.Movie;
import me.local.mcn.domain.model.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
}
