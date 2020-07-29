package com.mhp.coding.challenges.retry.outbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.core.entities.RetryTransferDto;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;

import com.mhp.coding.challenges.retry.inbound.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.logging.Logger;

/*
@Service
    Markiert eine Klasse als Dienstklasse, ist also eine Spezialform der @Component-Annotation
@Autowired
    Teilt Spring mit, wo es mittels Injection Objekte in andere Klassen einfügen soll.
    Die Injection erfolgt über den Typ des Objekts. standardmäßig werden diese Abhängigkeiten als required angesehen,
    mittels @Autowired (required = false) kann man dieses Verhalten jedoch ausschalten.
    Pro Klasse kann nur ein Konstruktor required sein.
@Retryable
    Methode versucht bei Fehler x erneut ausgeführt zu werden.
@Backoff
    Art und Weise des Wiederholungsversuchs
 */

@Service
@Validated
public class EmailNotificationSenderService implements NotificationSender {

    private static final Logger log = Logger.getLogger(EmailNotificationSenderService.class.getName());

    private RetryTransferDto dto = new RetryTransferDto();
    private final Integer maxRetries = 5;
    private final Integer timeShift = 5000;
    private final Integer multi = 2;
    private final RetryTemplate retryTemplate;
    private static final String SENDER_ADDRESS = "info@mhp.com";

    private JavaMailSender mailSender;

    @Autowired
    public EmailNotificationSenderService(RetryTemplate retryTemplate, JavaMailSender mailSender) {
        this.retryTemplate = retryTemplate;
        this.mailSender = mailSender;
    }

    @Async
    @Override
    @Retryable (
            value = {RuntimeException.class},
            maxAttempts = 5,
            backoff = @Backoff(
                    delay = 5000,
                    multiplier = 2
            )
    )
    public void sendEmail(@Valid @NotNull EmailNotification emailNotification) {
        log.info("EmailNotificationSenderService SENDEMAIL");

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            if (dto.count > maxRetries) {
                throw new RuntimeException((String.format("Failed to send email to recipient: %s", emailNotification.getRecipient())));
            } else {
                dto.setCount(dto.getCount() + 1);

                mailMessage.setFrom(SENDER_ADDRESS);
                /*
                mailMessage.setTo(emailNotification.recipient);
                mailMessage.setSubject(emailNotification.subject);
                mailMessage.setText(emailNotification.text);
                */
                mailMessage.setTo(emailNotification.getRecipient());
                mailMessage.setSubject(emailNotification.getSubject());
                mailMessage.setText(emailNotification.getText());

                mailSender.send(mailMessage);
            }
        } catch (Exception e) {
                throw new RuntimeException(String.format("Failed to send email to recipient: %s", emailNotification.getRecipient()));
            /*
            throw new RuntimeException(String.format("Failed to send email to recipient: $s", emailNotification.recipient));
             */
        }
    }
}

