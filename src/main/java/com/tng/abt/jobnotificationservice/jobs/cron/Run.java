package com.tng.abt.jobnotificationservice.jobs.cron;

import com.project.dingtalk.robot.send.RobotSendServices;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.jobs.etl.JobProcessInterface;
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
    private JobProcessInterface jobProcess;

    @Autowired
    private AbtJobRepository abtJobRepository;

    @Autowired
    private JobConfigurationRepository jobConfigurationRepository;

    @Autowired
    private RobotSendServices robotSendServices;

    @Value("${ding-talk.webhook.token}")
    private String token;

    @Scheduled(cron = "0 50 18 * * *")
    private void jobRun() {

        run();
    }

    private void run() {
        Boolean isEnable;

        isEnable = jobConfigurationRepository.findByIsEnableAndDeletedIsFalse(JobName.ADVANCE_SETTLEMENT.name());
        if (isAdvanceSettlementJobEnable(isEnable))
            return;

        isEnable = jobConfigurationRepository.findByIsEnableAndDeletedIsFalse(JobName.CARD_UPDATE_STATUS.name());
        if (isCardUpdateJobEnable(isEnable))
            return;

        isEnable = jobConfigurationRepository.findByIsEnableAndDeletedIsFalse(JobName.DORMANT_EXCLUSION.name());
        if (isDormantExclusionJobEnable(isEnable))
            return;

        isEnable = jobConfigurationRepository.findByIsEnableAndDeletedIsFalse(JobName.GP_BATCH.name());
        if (isGpBatchhJobEnable(isEnable))
            return;

        isEnable = jobConfigurationRepository.findByIsEnableAndDeletedIsFalse(JobName.REPORT_JOB.name());
        if (isReportJobEnable(isEnable))
            return;

    }

    private Boolean isAdvanceSettlementJobEnable(Boolean isEnable) {
        jobProcess = JobProcessInterface.jobProcess();
        jobProcess.setEnabled(isEnable);
        jobProcess.getAdvanceSettlementLatestJob();

        return isEnable;
    }

    private Boolean isCardUpdateJobEnable(Boolean isEnable) {

        jobProcess = JobProcessInterface.jobProcess();
        jobProcess.setEnabled(isEnable);
        jobProcess.getCardUpdateLatestJob();

        return isEnable;
    }

    private Boolean isDormantExclusionJobEnable(Boolean isEnable) {

        jobProcess = JobProcessInterface.jobProcess();
        jobProcess.setEnabled(isEnable);
        jobProcess.getDormantLatestJob();

        return isEnable;
    }

    private Boolean isGpBatchhJobEnable(Boolean isEnable) {

        jobProcess = JobProcessInterface.jobProcess();
        jobProcess.setEnabled(isEnable);
        jobProcess.getGpBatchLatestJob();

        return isEnable;
    }

    private Boolean isReportJobEnable(Boolean isEnable) {

        jobProcess = JobProcessInterface.jobProcess();
        jobProcess.setEnabled(isEnable);
        jobProcess.getReportLatestJob();

        return isEnable;
    }

}
