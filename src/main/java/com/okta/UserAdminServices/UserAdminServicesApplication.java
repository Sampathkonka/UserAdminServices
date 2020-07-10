package com.okta.UserAdminServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserAdminServicesApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(UserAdminServicesApplication.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");
		SpringApplication.run(UserAdminServicesApplication.class, args);
	}

}
