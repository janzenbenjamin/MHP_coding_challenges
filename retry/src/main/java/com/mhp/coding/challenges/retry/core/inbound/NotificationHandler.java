package com.mhp.coding.challenges.retry.core.inbound;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;

/*
Trennung Definition und Implementierung einer Funktionalität voneinander.

???
 */

public interface NotificationHandler {

    EmailNotification processEmailNotification(EmailNotification emailNotification);
}
