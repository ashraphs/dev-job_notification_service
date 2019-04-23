package com.tng.abt.jobnotificationservice.services;

import com.tng.abt.jobnotificationservice.entities.EpochJob;
import com.tng.abt.jobnotificationservice.enums.JobName;
import com.tng.abt.jobnotificationservice.repositories.EpochJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.tng.abt.jobnotificationservice.utils.Global.*;

@Slf4j
@Service
public class InitJob {

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
    private EpochJobRepository epochJobRepository;

    @Value("${dormant.job.timestamp.query}")
    private String dormantJobTimestampQuery;

    @Value("${card.update.job.timestamp.query}")
    private String cardUpdateQuery;

    @Value("${advance.settlement.job.timestamp.query}")
    private String advanceSettlementQuery;

    @Value("${gp.batch.timestamp.query}")
    private String gpBatchQuery;

    @Value("${job.report.timestamp.query}")
    private String reportQuery;

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
    private void initializeLatestJob() {

        log.info("######### Start Initialize #########");
        List<EpochJob> abtJobs = new ArrayList<>();

        Map<String, Date> init = epoch();
        if (init.size() > 0) {
            abtJobs.addAll(getLatestDormantJob());
            abtJobs.addAll(getLatestCardUpdateJob());
            abtJobs.addAll(getAdvanceSettlementJob());
            abtJobs.addAll(getGpBatchJob());
            abtJobs.addAll(getReportJob());
        }

        //truncate the table
        if (!abtJobs.isEmpty()) {
            jdbcTemplate.execute(truncateJobQuery);
        }

        //save the latest job to the temporary table
        for (EpochJob job : abtJobs) {
            job = epochJobRepository.save(job);
        }

        log.info("######### End Initialize #########");

    }

    private List<EpochJob> getLatestDormantJob() {
        HashMap<String, Date> job = epoch();
        String key = String.valueOf(job.get(JobName.DORMANT_EXCLUSION.name()));
        String query = dormantLatestJobQuery.replace("@", key);

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.DORMANT_EXCLUSION.name())
                        .abtJobStartDatetime(result.getTimestamp(JOB_START_DATETIME))
                        .abtJobEndDatetime(result.getTimestamp(JOB_END_DATETIME))
                        .build())
        );
    }

    private List<EpochJob> getLatestCardUpdateJob() {
        HashMap<String, Date> job = epoch();
        String key = String.valueOf(job.get(JobName.CARD_UPDATE_STATUS.name()));
        String query = cardUpdateLatestJobQuery.replace("@", key);

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.CARD_UPDATE_STATUS.name())
                        .abtJobStartDatetime(result.getTimestamp(JOB_START_DATETIME))
                        .abtJobEndDatetime(result.getTimestamp(JOB_END_DATETIME))
                        .build())
        );
    }

    private List<EpochJob> getAdvanceSettlementJob() {
        HashMap<String, Date> job = epoch();
        String key = String.valueOf(job.get(JobName.ADVANCE_SETTLEMENT.name()));
        String query = advanceSettlementJobQuery.replace("@", key);

        return new ArrayList<>(
                abtJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.ADVANCE_SETTLEMENT.name())
                        .abtJobStartDatetime(result.getTimestamp(JOB_START_DATETIME))
                        .abtJobEndDatetime(result.getTimestamp(JOB_END_DATETIME))
                        .build())
        );
    }

    private List<EpochJob> getGpBatchJob() {
        HashMap<String, Date> job = epoch();
        String key = String.valueOf(job.get(JobName.GP_BATCH.name()));
        String query = gpBatchJobQuery.replace("@", key);

        return new ArrayList<>(
                abtBatchJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.GP_BATCH.name())
                        .abtJobStartDatetime(result.getTimestamp(BATCH_JOB_START))
                        .abtJobEndDatetime(result.getTimestamp(BATCH_JOB_END))
                        .build())
        );
    }

    private List<EpochJob> getReportJob() {
        HashMap<String, Date> job = epoch();
        String key = String.valueOf(job.get(JobName.REPORT_JOB.name()));
        String query = reportJobQuery.replace("@", key);

        return new ArrayList<>(
                abtBatchJdbcTemplate.query(query, (result, rowNum) -> EpochJob.builder()
                        .jobName(JobName.REPORT_JOB.name())
                        .abtJobStartDatetime(result.getTimestamp(BATCH_JOB_START))
                        .abtJobEndDatetime(result.getTimestamp(BATCH_JOB_END))
                        .build())
        );
    }

    private HashMap<String, Date> epoch() {

        Date getDormantJobTimestamp = abtJdbcTemplate.queryForObject(dormantJobTimestampQuery, new Object[]{}, Date.class);
        Date getCardUpdateJobTimestamp = abtJdbcTemplate.queryForObject(cardUpdateQuery, new Object[]{}, Date.class);
        Date getAdvanceSettlementJobTimestamp = abtJdbcTemplate.queryForObject(advanceSettlementQuery, new Object[]{}, Date.class);
        Date getGpBatchJobTimestamp = abtBatchJdbcTemplate.queryForObject(gpBatchQuery, new Object[]{}, Date.class);
        Date getReportJobTimestamp = abtBatchJdbcTemplate.queryForObject(reportQuery, new Object[]{}, Date.class);

        Map<String, Date> map = new HashMap<>();
        map.put(JobName.DORMANT_EXCLUSION.name(), getDormantJobTimestamp);
        map.put(JobName.CARD_UPDATE_STATUS.name(), getCardUpdateJobTimestamp);
        map.put(JobName.ADVANCE_SETTLEMENT.name(), getAdvanceSettlementJobTimestamp);
        map.put(JobName.GP_BATCH.name(), getGpBatchJobTimestamp);
        map.put(JobName.REPORT_JOB.name(), getReportJobTimestamp);

        return new HashMap<>(map);
    }

}
