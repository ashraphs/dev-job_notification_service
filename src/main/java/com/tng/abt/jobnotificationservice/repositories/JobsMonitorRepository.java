package com.tng.abt.jobnotificationservice.repositories;

import com.tng.abt.jobnotificationservice.entities.JobsMonitor;
import com.tng.abt.jobnotificationservice.enums.NotificationStatus;

import java.util.Optional;

public interface JobsMonitorRepository extends MasterEntityRepository<JobsMonitor> {

    JobsMonitor findByJobNameIsAndNotificationStatus(String name, NotificationStatus status);

    Optional<JobsMonitor> findById(String id);
}
