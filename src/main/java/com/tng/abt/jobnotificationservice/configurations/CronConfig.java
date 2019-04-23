package com.tng.abt.jobnotificationservice.configurations;

import com.tng.abt.jobnotificationservice.repositories.CronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CronConfig {

    @Autowired
    private CronRepository cronRepository;

    @Bean
    public String jobNotificationMonitorInterval() {
        return cronRepository.findTopByName("job_notification_monitor").getValue();
    }
}

