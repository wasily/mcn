package me.local.mcn.controllers;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.model.Subscription;
import me.local.mcn.services.subscription.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping(path = "/subscriptions")
    public ResponseEntity<Subscription> addSubscription(@RequestBody Subscription subscription) {
        subscription.setLastUpdateTime(LocalDateTime.now());
        subscriptionService.addSubscription(subscription);
        return ResponseEntity.ok().build();
    }
}
