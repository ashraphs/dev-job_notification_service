package com.tng.abt.jobnotificationservice.jobs.jobprocess;

import com.tng.abt.jobnotificationservice.enums.JobName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class InitialJobProcess {

    @Qualifier("abtJdbcTemplate")
    @Autowired
    public JdbcTemplate abtJdbcTemplate;

    @Qualifier("abtBatchJdbcTemplate")
    @Autowired
    public JdbcTemplate abtBatchJdbcTemplate;

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

    /**
     * To get the latest job from database for the first time
     *
     * @return as set of key,value
     */
    @PostConstruct
    public HashMap<String, Date> epoch() {

        Map<String, Date> map = new HashMap<>();
        map.put(JobName.DORMANT_EXCLUSION.name(), getDormantJobTimestamp());
        map.put(JobName.CARD_UPDATE_STATUS.name(), getCardUpdateJobTimestamp());
        map.put(JobName.ADVANCE_SETTLEMENT.name(), getAdvanceSettlementJobTimestamp());
        map.put(JobName.GP_BATCH.name(), getGpBatchJobTimestamp());
        map.put(JobName.REPORT_JOB.name(), getReportJobTimestamp());

        return new HashMap<>(map);
    }

    private Date getDormantJobTimestamp() {
        return abtJdbcTemplate.queryForObject(dormantJobTimestampQuery, new Object[]{}, Date.class);
    }

    private Date getCardUpdateJobTimestamp() {
        return abtJdbcTemplate.queryForObject(cardUpdateQuery, new Object[]{}, Date.class);
    }

    private Date getAdvanceSettlementJobTimestamp() {
        return abtJdbcTemplate.queryForObject(advanceSettlementQuery, new Object[]{}, Date.class);
    }

    private Date getGpBatchJobTimestamp() {
        return abtBatchJdbcTemplate.queryForObject(gpBatchQuery, new Object[]{}, Date.class);
    }

    private Date getReportJobTimestamp() {
        return abtBatchJdbcTemplate.queryForObject(reportQuery, new Object[]{}, Date.class);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InitialJob {

        private JobName jobName;
        private String jobStartTime;


    }

}
