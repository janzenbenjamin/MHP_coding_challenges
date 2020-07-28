package com.mhp.coding.challenges.retry;

import com.mhp.coding.challenges.retry.core.entities.EmailNotification;
import com.mhp.coding.challenges.retry.outbound.EmailNotificationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@EnableRetry // Open retry mechanism label
public class RetryApplication {

	@Autowired
	private EmailNotification emailNotification;
	private EmailNotificationSenderService emailNotificationSenderService;

	public static void main(String[] args) {
		SpringApplication.run(RetryApplication.class, args);
	}

}

