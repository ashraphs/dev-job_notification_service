package com.tng.abt.jobnotificationservice.entities;

import com.touchngo.abt.utils.entities.MasterEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "mdt_system_configs")
@EqualsAndHashCode(callSuper = false)
public class SystemConfig extends MasterEntity {

    private static final long serialVersionUID = -3303600039102476430L;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private String type;

}