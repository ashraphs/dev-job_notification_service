package com.tng.abt.jobnotificationservice.entities;

import com.touchngo.abt.utils.entities.MasterEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "jn_abt_job")
public class AbtJob extends MasterEntity {

    @Column(name = "job_name")
    private String jobName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_query_datetime")
    private Date jobQueryDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_start_datetime")
    private Date abtJobStartDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_end_datetime")
    private Date abtJobEndDatetime;

    @Column(name = "is_successful_run")
    private Boolean isSuccessfulRun;

    @Column(name = "exit_code")
    private String exitCode;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

}
