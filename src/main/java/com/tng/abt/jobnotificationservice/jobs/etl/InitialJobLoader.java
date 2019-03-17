package com.tng.abt.jobnotificationservice.jobs.etl;

import com.tng.abt.jobnotificationservice.entities.AbtJob;
import com.tng.abt.jobnotificationservice.enums.ExitCode;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.repositories.AbtJobRepository;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.tng.abt.jobnotificationservice.utils.Global.*;


@Slf4j
@Service
public class InitialJobLoader {

    @Qualifier("abtJdbcTemplate")
    @Autowired
    public JdbcTemplate abtJdbcTemplate;

    @Qualifier("abtBatchJdbcTemplate")
    @Autowired
    public JdbcTemplate abtBatchJdbcTemplate;

    @Autowired
    private AbtJobRepository abtJobRepository;

    @Value("${job.status.dormant.query}")
    private String statusDormantQuery;

    @Value("${job.status.card.update.query}")
    private String cardUpdateQuery;

    @Value("${job.advance.settlement.query}")
    private String advanceSettlementQuery;

    @Value("${job.gp.batch.query}")
    private String gpBatchQuery;

    @Value("${job.report.status.query}")
    private String reportQuery;

    /**
     * Dormant Exclusion Job
     * Get latest Job from dormant exclusion job and save to the table jn_abt_job
     */
    @PostConstruct
    private void initDormantJob() {
        log.info("############## START LOAD INITIAL DATA ############## ");

        List<Job> dormantJob = new ArrayList<>(abtJdbcTemplate.query(statusDormantQuery, (resultSet, rowNum) -> Job.builder()
                .jobStart(resultSet.getTimestamp(JOB_START_DATETIME))
                .jobEnd(resultSet.getTimestamp(JOB_END_DATETIME))
                .isSuccessfulRun(resultSet.getBoolean(IS_SUCCESSFUL_RUN))
                .build()));

        AbtJob abtJob = new AbtJob();
        for (Job theJob : dormantJob) {
            abtJob.setJobName(JobName.DORMANT_EXCLUSION.name());
            abtJob.setAbtJobStartDatetime(theJob.getJobStart());
            abtJob.setAbtJobEndDatetime(theJob.getJobEnd());
            abtJob.setIsSuccessfulRun(theJob.getIsSuccessfulRun());

            if (!theJob.isSuccessfulRun)
                abtJob.setExitCode(ExitCode.FAILED.name());
            else
                abtJob.setExitCode(ExitCode.COMPLETED.name());

            abtJob = abtJobRepository.save(abtJob);
        }
    }

    /**
     * CARD UPDATE JOB
     * Get latest job from card update job audit and save to table jn_abt_job
     */
    @PostConstruct
    private void initCardUpdateJob() {
        List<Job> cardUpdateJob = new ArrayList<>(abtJdbcTemplate.query(cardUpdateQuery, (resultSet, rowNum) -> Job.builder()
                .jobStart(resultSet.getTimestamp(JOB_START_DATETIME))
                .jobEnd(resultSet.getTimestamp(JOB_END_DATETIME))
                .isSuccessfulRun(resultSet.getBoolean(IS_SUCCESSFUL_RUN))
                .errorMessage(resultSet.getString(ERROR_MESSAGE))
                .build()));

        AbtJob abtJob = new AbtJob();
        for (Job theJob : cardUpdateJob) {
            abtJob.setJobName(JobName.CARD_UPDATE_STATUS.name());
            abtJob.setAbtJobStartDatetime(theJob.getJobStart());
            abtJob.setAbtJobEndDatetime(theJob.getJobEnd());
            abtJob.setIsSuccessfulRun(theJob.getIsSuccessfulRun());
            abtJob.setErrorMessage(theJob.getErrorMessage());

            if (!theJob.isSuccessfulRun)
                abtJob.setExitCode(ExitCode.FAILED.name());
            else
                abtJob.setExitCode(ExitCode.COMPLETED.name());

            abtJob = abtJobRepository.save(abtJob);
        }
    }

    /**
     * ADVANCE SETTLEMENT JOB
     * Get latest job from adnvance settlement job and save to jn_abt_job
     */
    @PostConstruct
    private void initAdvanceSettlementJob() {
        List<Job> advanceSettlementJob = new ArrayList<>(abtJdbcTemplate.query(advanceSettlementQuery, (resultSet, rowNum) -> Job.builder()
                .jobStart(resultSet.getTimestamp(JOB_START_DATETIME))
                .jobEnd(resultSet.getTimestamp(JOB_END_DATETIME))
                .build()));

        AbtJob abtJob = new AbtJob();
        for (Job theJob : advanceSettlementJob) {
            abtJob.setJobName(JobName.ADVANCE_SETTLEMENT.name());
            abtJob.setAbtJobStartDatetime(theJob.getJobStart());
            abtJob.setAbtJobEndDatetime(theJob.getJobEnd());
            abtJob.setExitCode(ExitCode.UNKNOWN.name());
            abtJob = abtJobRepository.save(abtJob);
        }
    }

    /**
     * GP BATCH JOB
     * Get Latest job from GP Batch Job and save to jn_abt_job
     */
    @PostConstruct
    private void initGpBatchJob() {

        List<Job> gpBatchJob = new ArrayList<>(abtBatchJdbcTemplate.query(gpBatchQuery, (resultSet, rowNum) -> Job.builder()
                .jobStart(resultSet.getTimestamp("START_TIME"))
                .jobEnd(resultSet.getTimestamp("END_TIME"))
                .exitCode(resultSet.getString("EXIT_CODE"))
                .errorMessage(resultSet.getString("EXIT_MESSAGE"))
                .build()));

        AbtJob abtJob = new AbtJob();
        for (Job theJob : gpBatchJob) {
            abtJob.setJobName(JobName.GP_BATCH.name());
            abtJob.setAbtJobStartDatetime(theJob.getJobStart());
            abtJob.setAbtJobEndDatetime(theJob.getJobEnd());
            abtJob.setExitCode(theJob.getExitCode());
            abtJob.setErrorMessage(theJob.getErrorMessage());
            abtJob = abtJobRepository.save(abtJob);
        }
    }

    @PostConstruct
    private void initReportJobQuery() {

        List<Job> reportJob = new ArrayList<>(abtBatchJdbcTemplate.query(reportQuery, (resultSet, rowNum) -> Job.builder()
                .jobStart(resultSet.getTimestamp("START_TIME"))
                .jobEnd(resultSet.getTimestamp("END_TIME"))
                .exitCode(resultSet.getString("EXIT_CODE"))
                .errorMessage(resultSet.getString("EXIT_MESSAGE"))
                .build()));

        AbtJob abtJob = new AbtJob();
        for (Job theJob : reportJob) {
            abtJob.setJobName(JobName.REPORT_JOB.name());
            abtJob.setAbtJobStartDatetime(theJob.getJobStart());
            abtJob.setAbtJobEndDatetime(theJob.getJobEnd());
            abtJob.setExitCode(theJob.getExitCode());
            abtJob.setErrorMessage(theJob.getErrorMessage());
            abtJob = abtJobRepository.save(abtJob);
        }

        log.info("############## END LOAD INITIAL DATA ############## ");
    }


    @Data
    @Builder
    private static class Job {

        private String jobName;

        private Timestamp jobStart;

        private Timestamp jobEnd;

        private Boolean isSuccessfulRun;

        private String exitCode;

        private String errorMessage;

    }

}
