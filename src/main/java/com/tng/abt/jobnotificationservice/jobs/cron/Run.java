package com.tng.abt.jobnotificationservice.jobs.cron;

import com.project.dingtalk.robot.send.RobotSendServices;
import com.tng.abt.jobnotificationservice.repositories.AbtJobRepository;
import com.tng.abt.jobnotificationservice.repositories.JobConfigurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Run {

    @Autowired
    private AbtJobRepository abtJobRepository;

    @Autowired
    private JobConfigurationRepository jobConfigurationRepository;

    @Autowired
    private RobotSendServices robotSendServices;

    @Value("${ding-talk.webhook.token}")
    private String token;

    @Scheduled(cron = "0 */1 * * * *")
    private void jobRun() {


    }


}
