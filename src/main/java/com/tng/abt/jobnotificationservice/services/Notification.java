package com.tng.abt.jobnotificationservice.services;

import com.project.dingtalk.robot.send.RobotSendServices;
import com.tng.abt.jobnotificationservice.entities.EpochJob;
import com.tng.abt.jobnotificationservice.entities.JobsMonitor;
import com.tng.abt.jobnotificationservice.enums.NotificationStatus;
import com.tng.abt.jobnotificationservice.repositories.EpochJobRepository;
import com.tng.abt.jobnotificationservice.repositories.JobsMonitorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Notification {

    @Autowired
    private JobsMonitorRepository jobsMonitorRepository;

    @Autowired
    private EpochJobRepository epochJobRepository;

    @Autowired
    private RobotSendServices robotSendServices;

    @Autowired
    private Template template;

    @Autowired
    private LatestJob latestJob;

    @Value("${ding-talk.webhook.token}")
    private String token;

    @Scheduled(cron = "*/5 * * * * *")
    private void jobRun() {

        log.info("########## START JOB ##########");

        List<EpochJob> latest = latestJob.finalizeJob();
        for (EpochJob temp : latest) {
            JobsMonitor job = new JobsMonitor();
            job.setId(temp.getId());
            job.setJobName(temp.getJobName());
            job.setJobStartDate(temp.getAbtJobStartDatetime());
            job.setJobEndDate(temp.getAbtJobEndDatetime());
            job.setNotificationStatus(NotificationStatus.NEW);
            job = jobsMonitorRepository.save(job);

        }

        //Send  notification
        robotSendServices.sendTestMessage(token, template.body(NotificationStatus.NEW), null);

        //Update Status
        List<JobsMonitor> update = jobsMonitorRepository.findAll();
        for (JobsMonitor job : update) {
            job.setId(job.getId());
            job.setJobName(job.getJobName());
            job.setJobStartDate(job.getJobStartDate());
            job.setJobEndDate(job.getJobEndDate());
            job.setNotificationStatus(NotificationStatus.SENT);
            job = jobsMonitorRepository.save(job);
        }

        log.info("########## END JOB ##########");

    }

}
