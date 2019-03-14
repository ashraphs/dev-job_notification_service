package com.tng.abt.jobnotificationservice.jobs.etl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AbtJobStatus {

    @Value("${job.status.card.update.query}")
    private String jobCardStatusQuery;

    @Value("${job.advance.settlement.query}")
    private String jobAdvanceSettlementQuery;

    @Value("${job.report.status.query}")
    private String jobReportQuery;

    @Value("${job.gp.batch.query}")
    private String jobGpBatch;
}
