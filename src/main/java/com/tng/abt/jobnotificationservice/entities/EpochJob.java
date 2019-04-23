package com.tng.abt.jobnotificationservice.entities;

import com.touchngo.abt.utils.entities.MasterEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tmp_latest_job")
public class EpochJob extends MasterEntity {

    @Column(name = "job_name")
    private String jobName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_start_datetime")
    private Date abtJobStartDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_end_datetime")
    private Date abtJobEndDatetime;

}
