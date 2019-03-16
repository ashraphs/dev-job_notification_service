package com.tng.abt.jobnotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JobNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobNotificationServiceApplication.class, args);
    }

}
