package com.mhp.coding.challenges.retry.outbound;

import com.mhp.coding.challenges.retry.configuration.GlobalBeanConfiguration;
import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.AttributeAccessor;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration (
        classes = GlobalBeanConfiguration.class,
        loader = AnnotationConfigContextLoader.class
)
@ActiveProfiles("test")
class EmailNotificationSenderServiceTest {

    @Autowired
    private NotificationHandler notificationHandler;

    @Autowired
    private RetryTemplate retryTemplate;


    @Test
    void sendEmail() {

    }
}
