package me.local.mcn.services.notification;

import me.local.mcn.domain.Credentials;
import me.local.mcn.domain.Message;

public interface NotificationService {
    void notify(Credentials credentials, Message message);
}
