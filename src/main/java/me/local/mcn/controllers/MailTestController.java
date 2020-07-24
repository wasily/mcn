package me.local.mcn.controllers;

import lombok.RequiredArgsConstructor;
import me.local.mcn.domain.Credentials;
import me.local.mcn.domain.Message;
import me.local.mcn.services.notification.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MailTestController {
    private final NotificationService notificationService;

    @PostMapping(path = "/mailtest/{mailbox}")
    public ResponseEntity testMailSending(@PathVariable(name = "mailbox") String mailbox) {
        try {
            notificationService.notify(new Credentials(mailbox), new Message("test_email", "test-email"));
            return new ResponseEntity(HttpStatus.OK);
        } catch (MailException e) {
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
