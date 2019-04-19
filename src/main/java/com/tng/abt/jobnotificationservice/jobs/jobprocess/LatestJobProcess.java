package com.tng.abt.jobnotificationservice.jobs.jobprocess;

import com.tng.abt.jobnotificationservice.entities.AbtJob;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.repositories.AbtJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class LatestJobProcess {

    @Qualifier("abtJdbcTemplate")
    @Autowired
    private JdbcTemplate abtJdbcTemplate;

    @Autowired
    private InitialJobProcess initialJobProcess;

    @Autowired
    private AbtJobRepository abtJobRepository;

    @Value("${dormant.latest.job.query}")
    private String dormantLatestJobQuery;

    @Value("${card-update.latest.job.query}")
    private String cardUpdateLatestJobQuery;

    @Value("${advance-settlement.latest.job.query}")
    private String advanceSettlementJobQuery;

    @Value("${gp-batch.latest.job.query}")
    private String gpBatchJobQuery;

    @Value("${report.latest.job.query}")
    private String reportJobQuery;


    @PostConstruct
    private void findLatestJobAndSave() {
        List<AbtJob> abtJobs = new ArrayList<>();
        abtJobs.addAll(getLatestDormantJob());
        abtJobs.addAll(getLatestCardUpdateJob());
        abtJobs.addAll(getAdvanceSettlementJob());
        abtJobs.addAll(getGpBatchJob());
        abtJobs.addAll(getReportJob());

        for (AbtJob job : abtJobs) {
            job = abtJobRepository.save(job);
        }

    }

    private List<AbtJob> getLatestDormantJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.DORMANT_EXCLUSION.name()));
        String query = dormantLatestJobQuery.replace("@", key);

        List<AbtJob> dormant = new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> AbtJob.builder()
                        .jobName(JobName.DORMANT_EXCLUSION.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .isSuccessfulRun(result.getBoolean("is_successful_run"))
                        .build())
        );
        return dormant;
    }

    private List<AbtJob> getLatestCardUpdateJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.CARD_UPDATE_STATUS.name()));
        String query = cardUpdateLatestJobQuery.replace("@", key);

        List<AbtJob> card = new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> AbtJob.builder()
                        .jobName(JobName.CARD_UPDATE_STATUS.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .isSuccessfulRun(result.getBoolean("is_successful_run"))
                        .build())
        );
        return card;
    }

    private List<AbtJob> getAdvanceSettlementJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.ADVANCE_SETTLEMENT.name()));
        String query = advanceSettlementJobQuery.replace("@", key);

        List<AbtJob> settlement = new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> AbtJob.builder()
                        .jobName(JobName.ADVANCE_SETTLEMENT.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .build())
        );
        return settlement;
    }

    private List<AbtJob> getGpBatchJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.GP_BATCH.name()));
        String query = gpBatchJobQuery.replace("@", key);

        List<AbtJob> gpBatch = new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> AbtJob.builder()
                        .jobName(JobName.GP_BATCH.name())
                        .abtJobStartDatetime(result.getTimestamp("START_TIME"))
                        .abtJobEndDatetime(result.getTimestamp("END_TIME"))
                        .exitCode(result.getString("EXIT_CODE"))
                        .build())
        );
        return gpBatch;
    }

    private List<AbtJob> getReportJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.REPORT_JOB.name()));
        String query = reportJobQuery.replace("@", key);

        List<AbtJob> report = new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> AbtJob.builder()
                        .jobName(JobName.REPORT_JOB.name())
                        .abtJobStartDatetime(result.getTimestamp("START_TIME"))
                        .abtJobEndDatetime(result.getTimestamp("END_TIME"))
                        .exitCode(result.getString("EXIT_CODE"))
                        .build())
        );
        return report;
    }

}
