package com.tng.abt.jobnotificationservice.entities;

import com.touchngo.abt.utils.entities.MasterEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "jbn_job_configs")
public class JobConfiguration extends MasterEntity {

    @Column(name = "name")
    private String jobName;

    @Column(name = "is_enable")
    private Boolean isEnable;

}
