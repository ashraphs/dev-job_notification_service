package com.tng.abt.jobnotificationservice.services;

import com.project.dingtalk.robot.send.RobotSendServices;
import com.tng.abt.jobnotificationservice.entities.EpochJob;
import com.tng.abt.jobnotificationservice.entities.JobsMonitor;
import com.tng.abt.jobnotificationservice.entities.SystemConfig;
import com.tng.abt.jobnotificationservice.enums.NotificationStatus;
import com.tng.abt.jobnotificationservice.repositories.JobsMonitorRepository;
import com.tng.abt.jobnotificationservice.repositories.SystemConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tng.abt.jobnotificationservice.utils.Global.DING_TALK_TOKEN;

@Slf4j
@Component
public class Notification {

    @Autowired
    private JobsMonitorRepository jobsMonitorRepository;

    @Autowired
    private SystemConfigRepository configRepository;

    @Autowired
    private RobotSendServices robotSendServices;

    @Autowired
    private Template template;

    @Autowired
    private LatestJob latestJob;

    @Scheduled(cron = "#{@jobNotificationMonitorInterval}")
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
        SystemConfig token = configRepository.findTopByNameIs(DING_TALK_TOKEN);

        log.info("Token: {}", token.getValue());
        if (token != null) {

            //send notification
            robotSendServices.sendTestMessage(token.getValue(), template.body(NotificationStatus.NEW), null);

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
        } else {
            log.error("Token cannot be null !!!");
        }

        log.info("########## END JOB ##########");

    }

}