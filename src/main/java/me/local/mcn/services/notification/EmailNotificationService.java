package me.local.mcn.services.notification;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.Credentials;
import me.local.mcn.domain.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotificationService implements NotificationService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String serverMailbox;

    @Override
    public void notify(Credentials credentials, Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(credentials.getEmail());
        mailMessage.setFrom(serverMailbox);
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getText());
        mailSender.send(mailMessage);
    }
}
