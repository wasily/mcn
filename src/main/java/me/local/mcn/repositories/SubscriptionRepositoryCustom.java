package me.local.mcn.repositories;

import java.time.LocalDateTime;

public interface SubscriptionRepositoryCustom {
    void updateLastSearchTime(String id, LocalDateTime newLocalDateTime);
}
