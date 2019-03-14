package com.tng.abt.jobnotificationservice.jobs.etl;

import com.tng.abt.jobnotificationservice.entities.AbtJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Slf4j
@Component
public class JobAbtCoreLoader {

    @Autowired
    @Qualifier("abtJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Value("${job.status.dormant.query}")
    private String jobDormantQuery;

    public List<AbtJob> findDormantJob(Boolean isSuccesfulRun, Date jobStartTime) {

    }


}
