package com.tng.abt.jobnotificationservice.jobs.etl;

import com.tng.abt.jobnotificationservice.repositories.AbtJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class InitialJobLoader {

    @Autowired
    private AbtJobRepository abtJobRepository;


    @Value("${job.status.dormant.query}")
    private String jobInitialDormantQuery;

    @PostConstruct
    public Void dormantJobLoader(){



    }
}
