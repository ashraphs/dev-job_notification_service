package com.tng.abt.jobnotificationservice.repositories;

import com.tng.abt.jobnotificationservice.entities.JobsMonitor;
import com.tng.abt.jobnotificationservice.enums.NotificationStatus;

import java.util.List;

public interface JobsMonitorRepository extends MasterEntityRepository<JobsMonitor> {

    JobsMonitor findByNotificationStatusAndJobNameIs(NotificationStatus status, String name);

    List<JobsMonitor>findAllByNotificationStatusIs(NotificationStatus status);

}
