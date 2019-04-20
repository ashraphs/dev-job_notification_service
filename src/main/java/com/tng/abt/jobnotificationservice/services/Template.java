package com.tng.abt.jobnotificationservice.services;

import com.tng.abt.jobnotificationservice.entities.JobsMonitor;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.enums.NotificationStatus;
import com.tng.abt.jobnotificationservice.repositories.JobsMonitorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Template {

    @Autowired
    private JobsMonitorRepository abtJobRepository;

    public String body(NotificationStatus status) {
        String firstHeader = "<----- Job Monitoring ------>\n";

        return firstHeader + dormantJobBody(status) + cardUpdateJobBody(status) + advanceSettlementJobBody(status) + gpBatchJobBody(status) + reportJobBody(status);
    }

    private String dormantJobBody(NotificationStatus status) {
        JobsMonitor dormant = abtJobRepository.findByNotificationStatusAndJobNameIs(status, JobName.DORMANT_EXCLUSION.name());
        if (dormant != null) {
            String name = "---- " + dormant.getJobName() + " ----\n";

            String startDate = "Job Start: " + dormant.getJobStartDate() + "\n";
            String endDate = "Job End: " + dormant.getJobEndDate() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String cardUpdateJobBody(NotificationStatus status) {
        JobsMonitor cardUpdate = abtJobRepository.findByNotificationStatusAndJobNameIs(status, JobName.CARD_UPDATE_STATUS.name());
        if (cardUpdate != null) {
            String name = "---- " + cardUpdate.getJobName() + " ----\n";

            String startDate = "Job Start: " + cardUpdate.getJobStartDate() + "\n";
            String endDate = "Job End: " + cardUpdate.getJobEndDate() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String advanceSettlementJobBody(NotificationStatus status) {
        JobsMonitor advanceSettlement = abtJobRepository.findByNotificationStatusAndJobNameIs(status, JobName.ADVANCE_SETTLEMENT.name());
        if (advanceSettlement != null) {
            String name = "---- " + advanceSettlement.getJobName() + " ----\n";

            String startDate = "Job Start: " + advanceSettlement.getJobStartDate() + "\n";
            String endDate = "Job End: " + advanceSettlement.getJobEndDate() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String gpBatchJobBody(NotificationStatus status) {
        JobsMonitor gpBatchJob = abtJobRepository.findByNotificationStatusAndJobNameIs(status, JobName.GP_BATCH.name());
        if (gpBatchJob != null) {
            String name = "---- " + gpBatchJob.getJobName() + " ----\n";

            String startDate = "Job Start: " + gpBatchJob.getJobStartDate();
            String endDate = "Job End: " + gpBatchJob.getJobEndDate() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String reportJobBody(NotificationStatus status) {
        JobsMonitor report = abtJobRepository.findByNotificationStatusAndJobNameIs(status, JobName.REPORT_JOB.name());
        if (report != null) {
            String name = "---- " + report.getJobName() + " ----\n";

            String startDate = "Job Start: " + report.getJobStartDate() + "\n";
            String endDate = "Job End: " + report.getJobEndDate();

            return name + startDate + endDate;
        } else
            return null;
    }
}
