package com.tng.abt.jobnotificationservice.services;

import com.tng.abt.jobnotificationservice.entities.EpochJob;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.repositories.EpochJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LatestJob {

    @Qualifier("abtJdbcTemplate")
    @Autowired
    private JdbcTemplate abtJdbcTemplate;

    @Qualifier("abtBatchJdbcTemplate")
    @Autowired
    private JdbcTemplate abtBatchJdbcTemplate;

    @Autowired
    private EpochJobRepository epochJobRepository;

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


    List<EpochJob> finalizeJob() {
        List<EpochJob> job = new ArrayList<>();
        job.addAll(latestAdvanceSettlementJob());
        job.addAll(latestCardUpdateStatusJob());
        job.addAll(latestDormantExclusionJob());
        job.addAll(latestGpBatchJob());
        job.addAll(latestReportJob());

        return job;
    }

    private List<EpochJob> latestAdvanceSettlementJob() {
        EpochJob jobStart = epochJobRepository.findAllByJobName(JobName.ADVANCE_SETTLEMENT.name());
        String query = advanceSettlementJobQuery.replace("@", jobStart.getAbtJobStartDatetime().toString());

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.ADVANCE_SETTLEMENT.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .build())
        );
    }

    private List<EpochJob> latestCardUpdateStatusJob() {
        EpochJob jobStart = epochJobRepository.findAllByJobName(JobName.CARD_UPDATE_STATUS.name());
        String query = cardUpdateLatestJobQuery.replace("@", jobStart.getAbtJobStartDatetime().toString());

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.CARD_UPDATE_STATUS.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .build())
        );
    }

    private List<EpochJob> latestDormantExclusionJob() {
        EpochJob jobStart = epochJobRepository.findAllByJobName(JobName.DORMANT_EXCLUSION.name());
        String query = dormantLatestJobQuery.replace("@", jobStart.getAbtJobStartDatetime().toString());

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.DORMANT_EXCLUSION.name())
                        .abtJobStartDatetime(result.getTimestamp("job_start_datetime"))
                        .abtJobEndDatetime(result.getTimestamp("job_end_datetime"))
                        .build())
        );
    }

    private List<EpochJob> latestGpBatchJob() {
        EpochJob jobStart = epochJobRepository.findAllByJobName(JobName.GP_BATCH.name());
        String query = gpBatchJobQuery.replace("@", jobStart.getAbtJobStartDatetime().toString());

        return new ArrayList<>(
                abtBatchJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.GP_BATCH.name())
                        .abtJobStartDatetime(result.getTimestamp("START_TIME"))
                        .abtJobEndDatetime(result.getTimestamp("END_TIME"))
                        .build())
        );
    }

    private List<EpochJob> latestReportJob() {
        EpochJob jobStart = epochJobRepository.findAllByJobName(JobName.REPORT_JOB.name());
        String query = reportJobQuery.replace("@", jobStart.getAbtJobStartDatetime().toString());

        return new ArrayList<>(
                abtBatchJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.REPORT_JOB.name())
                        .abtJobStartDatetime(result.getTimestamp("START_TIME"))
                        .abtJobEndDatetime(result.getTimestamp("END_TIME"))
                        .build())
        );
    }


}
