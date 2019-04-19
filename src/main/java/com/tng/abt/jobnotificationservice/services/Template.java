package com.tng.abt.jobnotificationservice.services;

import com.tng.abt.jobnotificationservice.entities.AbtJob;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.repositories.AbtJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class Template {

    @Autowired
    private AbtJobRepository abtJobRepository;

    @PostConstruct
    public void testTemplate() {
        log.info(">>>>>>>>> Message Template <<<<<<<\n");
        log.info("{}", reportTemplate());

    }

    private String reportTemplate() {
        String firstHeader = "<----- Job Monitoring ------>\n";

        return firstHeader + dormantJobBody() + cardUpdateJobBody() + advanceSettlementJobBody() + gpBatchJobBody() + reportJobBody();
    }


    private String dormantJobBody() {
        AbtJob dormant = abtJobRepository.findAllByJobName(JobName.DORMANT_EXCLUSION.name());
        if (dormant != null) {
            String name = "###### " + dormant.getJobName() + " ######";

            String startDate = "Job Start: " + dormant.getAbtJobStartDatetime() + "\n";
            String endDate = "Job End: " + dormant.getAbtJobStartDatetime() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String cardUpdateJobBody() {
        AbtJob cardUpdate = abtJobRepository.findAllByJobName(JobName.CARD_UPDATE_STATUS.name());
        if (cardUpdate != null) {
            String name = "###### " + cardUpdate.getJobName() + " ######\n";

            String startDate = "Job Start: " + cardUpdate.getAbtJobStartDatetime() + "\n";
            String endDate = "Job End: " + cardUpdate.getAbtJobStartDatetime() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String advanceSettlementJobBody() {
        AbtJob advanceSettlement = abtJobRepository.findAllByJobName(JobName.ADVANCE_SETTLEMENT.name());
        if (advanceSettlement != null) {
            String name = "###### " + advanceSettlement.getJobName() + " ######\n";

            String startDate = "Job Start: " + advanceSettlement.getAbtJobStartDatetime() + "\n";
            String endDate = "Job End: " + advanceSettlement.getAbtJobStartDatetime() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String gpBatchJobBody() {
        AbtJob gpBatchJob = abtJobRepository.findAllByJobName(JobName.GP_BATCH.name());
        if (gpBatchJob != null) {
            String name = "###### " + gpBatchJob.getJobName() + " ######\n";

            String startDate = "Job Start: " + gpBatchJob.getAbtJobStartDatetime() + "\n";
            String endDate = "Job End: " + gpBatchJob.getAbtJobStartDatetime() + "\n";

            return name + startDate + endDate;
        } else
            return null;
    }

    private String reportJobBody() {
        AbtJob report = abtJobRepository.findAllByJobName(JobName.REPORT_JOB.name());
        if (report != null) {
            String name = "###### " + report.getJobName() + " ######\n";

            String startDate = "Job Start: " + report.getAbtJobStartDatetime() + "\n";
            String endDate = "Job End: " + report.getAbtJobStartDatetime();

            return name + startDate + endDate;
        } else
            return null;
    }
}
