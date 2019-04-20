package com.tng.abt.jobnotificationservice.jobs.cron;

import com.project.dingtalk.robot.send.RobotSendServices;
import com.tng.abt.jobnotificationservice.entities.EpochJob;
import com.tng.abt.jobnotificationservice.entities.JobsMonitor;
import com.tng.abt.jobnotificationservice.enums.NotificationStatus;
import com.tng.abt.jobnotificationservice.repositories.EpochJobRepository;
import com.tng.abt.jobnotificationservice.repositories.JobsMonitorRepository;
import com.tng.abt.jobnotificationservice.services.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class Run {

    @Autowired
    private JobsMonitorRepository jobsMonitorRepository;

    @Autowired
    private EpochJobRepository epochJobRepository;

    @Autowired
    private RobotSendServices robotSendServices;

    @Autowired
    private Template template;

    @Value("${ding-talk.webhook.token}")
    private String token;

    @Scheduled(cron = "*/5 * * * * *")
    private void jobRun() {
        jobRun(true);
    }

    public void jobRun(Boolean resend) {

        List<EpochJob> jobs = epochJobRepository.findAllByDeletedIsFalse();
        if (jobs != null) {
            JobsMonitor monitor = new JobsMonitor();

            for (EpochJob theJob : jobs) {
                monitor.setJobName(theJob.getJobName());
                monitor.setJobStartDate(theJob.getAbtJobStartDatetime());
                monitor.setJobEndDate(theJob.getAbtJobEndDatetime());
                monitor.setNotificationStatus(NotificationStatus.NEW);
                monitor = jobsMonitorRepository.save(monitor);

            }
        }

        //send the message
        List<JobsMonitor> jobsMonitorList = jobsMonitorRepository.findAllByNotificationStatusIs(NotificationStatus.NEW);
        if (jobsMonitorList != null) {
            robotSendServices.sendTestMessage(token, template.body(NotificationStatus.NEW), null);

            JobsMonitor status = new JobsMonitor();
            for (JobsMonitor theJob : jobsMonitorList) {

                status.setJobName(theJob.getJobName());
                status.setJobStartDate(theJob.getJobStartDate());
                status.setJobEndDate(theJob.getJobEndDate());
                status.setNotificationStatus(NotificationStatus.SENT);

                status = jobsMonitorRepository.save(status);
            }
        }

    }

}
