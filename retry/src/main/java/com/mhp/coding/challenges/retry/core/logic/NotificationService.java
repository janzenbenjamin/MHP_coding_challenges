package com.mhp.coding.challenges.retry.core.logic;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.core.entities.RetryTransferDto;
import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class NotificationService implements NotificationHandler {

    private NotificationSender notificationSender;
    private RetryTransferDto dto = new RetryTransferDto();
    private final Integer maxRetries = 5;

    public NotificationService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    @Override
    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 5,
            backoff = @Backoff(
                    delay = 5000,
                    multiplier = 2
            )
    )
    public EmailNotification processEmailNotification(EmailNotification emailNotification) {

        if (dto.count < maxRetries) {
            dto.setCount(dto.getCount() + 1);

            notificationSender.sendEmail(emailNotification);
            return emailNotification;
        } else {
            throw new RuntimeException((String.format("process failed: %s", emailNotification)));
        }
    }
}
