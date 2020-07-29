package com.mhp.coding.challenges.retry.inbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;
import com.mhp.coding.challenges.retry.outbound.EmailNotificationSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

/*
@RestController
    @Controller markiert eine Klasse als Controller.

    Warum @RestController???

@RequestMapping
    bildet URLs auf Klassen oder Methoden ab.
    @PostMapping; @GetMapping; @PutMapping --> verkürzte schreibweise.
@RequestBody
    Annotiert Methodenargumente von Anfragehandlern und deutet an, dass diese auf
    den Wert des HTTP Anfrage-Body gebunden werden soll.

 */
@RestController
@RequestMapping("/v1/emails")
public class EmailController {

    private NotificationHandler notificationHandler;
    private EmailNotificationSenderService emailNotificationSenderService;

    public EmailController(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @PostMapping
    public ResponseEntity<EmailNotification> createEmailNotification(@RequestBody EmailNotification emailNotification) {
        EmailNotification emailNotificationResult = notificationHandler.processEmailNotification(emailNotification);
        return ResponseEntity.ok(emailNotificationResult);
    }

    @PostMapping("send-mail")
    public String send(EmailNotification emailNotification) {
        /*
        emailNotification.recipient = "recipient@test.de";
        emailNotification.subject = "TEST";
        emailNotification.text = "TRY OR DIE";
         */
        emailNotification.setRecipient("recipient@test.de");
        emailNotification.setSubject("TEST");
        emailNotification.setText("TRY OR DIE");

        try {
            emailNotificationSenderService.sendEmail(emailNotification);
        } catch (MailException mailException) {
            System.out.println(mailException);
        }
        return "Congratulations! Your mail has been send to the user.";
    }
}
