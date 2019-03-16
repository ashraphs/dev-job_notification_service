package com.tng.abt.jobnotificationservice.entities;

import com.touchngo.abt.utils.entities.MasterEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "jn_job_audits")
public class JobAudit extends MasterEntity {

    @Column(name = "job_name")
    private String jobName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_start_datetime", columnDefinition = "DATETIME(3)")
    private Date jobStartDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_end_datetime", columnDefinition = "DATETIME(3)")
    private Date jobEndDatetime;

    @Column(name = "is_successful_run")
    private boolean isSuccessfulRun;

    @Column(name = "created_date", columnDefinition = "DATETIME(3)")
    private Date createdDate;

    @Column(name = "job_runtime")
    private String runtime;

    @Column(name = "error_message")
    private String errorMessage;

}
