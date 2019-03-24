package com.tng.abt.jobnotificationservice.entities;

import com.touchngo.abt.utils.entities.MasterEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mdt_job_configuration")
public class JobConfiguration extends MasterEntity {

    @Column(name = "name")
    private String jobName;

    @Column(name = "is_enable")
    private Boolean isEnable;

}
