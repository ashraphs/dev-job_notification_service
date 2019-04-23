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
        String firstHeader = "|--JOB MONITORING\n\n";

        return firstHeader + dormantJobBody(status) + cardUpdateJobBody(status) + advanceSettlementJobBody(status) + gpBatchJobBody(status) + reportJobBody(status);
    }

    private String dormantJobBody(NotificationStatus status) {
        JobsMonitor dormant = abtJobRepository.findTopByJobNameIsAndNotificationStatusOrderByCreatedDateDesc(JobName.DORMANT_EXCLUSION.name(), status);

        if (dormant != null && dormant.getNotificationStatus().equals(NotificationStatus.NEW)) {
            String name = "|-- " + dormant.getJobName() + "\n";

            String startDate = "Job Start: " + dormant.getJobStartDate() + "\n";
            String endDate = "Job End: " + dormant.getJobEndDate() + "\n\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String cardUpdateJobBody(NotificationStatus status) {
        JobsMonitor cardUpdate = abtJobRepository.findTopByJobNameIsAndNotificationStatusOrderByCreatedDateDesc(JobName.CARD_UPDATE_STATUS.name(), status);
        if (cardUpdate != null && cardUpdate.getNotificationStatus().equals(NotificationStatus.NEW)) {
            String name = "|-- " + cardUpdate.getJobName() + "\n";

            String startDate = "Job Start: " + cardUpdate.getJobStartDate() + "\n";
            String endDate = "Job End: " + cardUpdate.getJobEndDate() + "\n\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String advanceSettlementJobBody(NotificationStatus status) {
        JobsMonitor advanceSettlement = abtJobRepository.findTopByJobNameIsAndNotificationStatusOrderByCreatedDateDesc(JobName.ADVANCE_SETTLEMENT.name(), status);
        if (advanceSettlement != null && advanceSettlement.getNotificationStatus().equals(NotificationStatus.NEW)) {
            String name = "|-- " + advanceSettlement.getJobName() + "\n";

            String startDate = "Job Start: " + advanceSettlement.getJobStartDate() + "\n";
            String endDate = "Job End: " + advanceSettlement.getJobEndDate() + "\n\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String gpBatchJobBody(NotificationStatus status) {
        JobsMonitor gpBatchJob = abtJobRepository.findTopByJobNameIsAndNotificationStatusOrderByCreatedDateDesc(JobName.GP_BATCH.name(), status);
        if (gpBatchJob != null && gpBatchJob.getNotificationStatus().equals(NotificationStatus.NEW)) {
            String name = "|-- " + gpBatchJob.getJobName() + "\n";

            String startDate = "Job Start: " + gpBatchJob.getJobStartDate() + "\n";
            String endDate = "Job End: " + gpBatchJob.getJobEndDate() + "\n\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String reportJobBody(NotificationStatus status) {
        JobsMonitor report = abtJobRepository.findTopByJobNameIsAndNotificationStatusOrderByCreatedDateDesc(JobName.REPORT_JOB.name(), status);
        if (report != null && report.getNotificationStatus().equals(NotificationStatus.NEW)) {
            String name = "|-- " + report.getJobName() + "\n";

            String startDate = "Job Start: " + report.getJobStartDate() + "\n";
            String endDate = "Job End: " + report.getJobEndDate();

            return name + startDate + endDate;
        } else
            return null;
    }
}
