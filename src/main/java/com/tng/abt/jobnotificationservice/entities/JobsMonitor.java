package com.tng.abt.jobnotificationservice.entities;

import com.tng.abt.jobnotificationservice.enums.NotificationStatus;
import com.touchngo.abt.utils.entities.MasterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "jbn_jobs_monitor")
public class JobsMonitor extends MasterEntity {

    @Column(name = "job_name")
    private String jobName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_start_datetime")
    private Date jobStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "job_end_datetime")
    private Date jobEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status")
    private NotificationStatus notificationStatus;


}
