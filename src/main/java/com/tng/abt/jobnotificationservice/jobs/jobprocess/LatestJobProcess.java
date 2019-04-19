package com.tng.abt.jobnotificationservice.jobs.jobprocess;

import com.tng.abt.jobnotificationservice.entities.EpochJob;
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

    @Qualifier("abtBatchJdbcTemplate")
    @Autowired
    private JdbcTemplate abtBatchJdbcTemplate;

    @Qualifier("localJdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    @Value("${truncate.job.query}")
    private String truncateJobQuery;


    @PostConstruct
    private void findLatestJobAndSave() {
        List<EpochJob> abtJobs = new ArrayList<>();
        abtJobs.addAll(getLatestDormantJob());
        abtJobs.addAll(getLatestCardUpdateJob());
        abtJobs.addAll(getAdvanceSettlementJob());
        abtJobs.addAll(getGpBatchJob());
        abtJobs.addAll(getReportJob());

        //truncate the table
        if (abtJobs.size() > 0) {
            jdbcTemplate.execute(truncateJobQuery);
        }

        for (EpochJob job : abtJobs) {
            job = abtJobRepository.save(job);
        }

    }

    private List<EpochJob> getLatestDormantJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.DORMANT_EXCLUSION.name()));
        String query = dormantLatestJobQuery.replace("@", key);

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.DORMANT_EXCLUSION.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .build())
        );
    }

    private List<EpochJob> getLatestCardUpdateJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.CARD_UPDATE_STATUS.name()));
        String query = cardUpdateLatestJobQuery.replace("@", key);

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.CARD_UPDATE_STATUS.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .build())
        );
    }

    private List<EpochJob> getAdvanceSettlementJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.ADVANCE_SETTLEMENT.name()));
        String query = advanceSettlementJobQuery.replace("@", key);

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.ADVANCE_SETTLEMENT.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .build())
        );
    }

    private List<EpochJob> getGpBatchJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.GP_BATCH.name()));
        String query = gpBatchJobQuery.replace("@", key);

        return new ArrayList<>(
                abtBatchJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.GP_BATCH.name())
                        .abtJobStartDatetime(result.getTimestamp("START_TIME"))
                        .abtJobEndDatetime(result.getTimestamp("END_TIME"))
                        .build())
        );
    }

    private List<EpochJob> getReportJob() {
        HashMap<String, Date> job = initialJobProcess.epoch();
        String key = String.valueOf(job.get(JobName.REPORT_JOB.name()));
        String query = reportJobQuery.replace("@", key);

        return new ArrayList<>(
                abtBatchJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.REPORT_JOB.name())
                        .abtJobStartDatetime(result.getTimestamp("START_TIME"))
                        .abtJobEndDatetime(result.getTimestamp("END_TIME"))
                        .build())
        );
    }

}
