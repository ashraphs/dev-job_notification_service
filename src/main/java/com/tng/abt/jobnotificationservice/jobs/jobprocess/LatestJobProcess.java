package com.tng.abt.jobnotificationservice.jobs.jobprocess;

import com.tng.abt.jobnotificationservice.repositories.AbtJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LatestJobProcess {

    @Qualifier("abtJdbcTemplate")
    @Autowired
    private JdbcTemplate ab;

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



}
