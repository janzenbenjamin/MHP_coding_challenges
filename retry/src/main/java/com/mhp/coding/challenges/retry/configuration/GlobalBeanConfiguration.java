package com.mhp.coding.challenges.retry.configuration;

import com.mhp.coding.challenges.retry.core.entities.RetryOperations;
import com.mhp.coding.challenges.retry.core.inbound.NotificationHandler;
import com.mhp.coding.challenges.retry.core.logic.NotificationService;
import com.mhp.coding.challenges.retry.core.outbound.NotificationSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.Properties;

/*
@Configuration
    Markiert eine Klasse als Konfigurationsklasse für Bean Definitionen.
@ComponentScan
    Erlaubt das automatische Auffinden von Beans
@Bean
    Markiert eine Methode, die ein Bean-Objekt erzeugt, konfiguriert und initialisiert,
    die vom Spring IOC Container im ApplicationCentext verwaltet werden soll.
    Außerhalb der Confi-Class ist @Bean eine einfache Factory-Methode.
    Methoden namens close() oder shutdown() werden automatisch beim beenden des COntainers ausgeführt.
 */

@Configuration
@ComponentScan
@EnableRetry
public class GlobalBeanConfiguration {

    private String host = "localhost";
    private int port = 1025;

    @Bean
    public NotificationHandler notificationHandler(NotificationSender notificationSender) {
        return new NotificationService(notificationSender);
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(5000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);
        retryTemplate.setRetryPolicy(retryPolicy);

        retryTemplate.registerListener(new RetryOperations());

        return retryTemplate;
    }

    @Bean
    public SimpleMailMessage simpleMessageTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "This is the test email template for your email:\n%s\n"
        );
        return message;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");

        javaMailSender.setJavaMailProperties(properties);

        return javaMailSender;
    }
}
