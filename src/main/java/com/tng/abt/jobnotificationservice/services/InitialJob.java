package com.tng.abt.jobnotificationservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitialJob {

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

    @PostConstruct
    public String getFirstTimeDormantJob(){
        return abtJdbcTemplate.queryForObject(dormantJobTimestampQuery,String.class);
    }

    @PostConstruct
    public String getFirsTimeCardUpdateJob() {
        return abtJdbcTemplate.queryForObject(cardUpdateQuery,String.class);
    }

    @PostConstruct
    public String getFirstTimeAdvanceSettlementJob() {
        return abtJdbcTemplate.queryForObject(advanceSettlementQuery,String.class);
    }

    @PostConstruct
    public String getFirstTimeGpBatchJob() {
        return abtJdbcTemplate.queryForObject(gpBatchQuery,String.class);
    }

    @PostConstruct
    public String getReportJobFirstTimeJob() {
        return abtJdbcTemplate.queryForObject(reportQuery,String.class);
    }


}
