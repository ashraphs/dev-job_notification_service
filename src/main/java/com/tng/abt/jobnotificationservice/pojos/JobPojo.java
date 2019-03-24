package com.tng.abt.jobnotificationservice.pojos;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class JobPojo {

    private String jobName;

    private Timestamp jobStart;

    private Timestamp jobEnd;

    private Boolean isSuccessfulRun;

    private String exitCode;

    private String errorMessage;

}
