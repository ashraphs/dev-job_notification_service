package com.tng.abt.jobnotificationservice.repositories;

import com.tng.abt.jobnotificationservice.entities.SystemConfig;

public interface SystemConfigRepository extends MasterEntityRepository<SystemConfig> {

    SystemConfig findTopByNameIs(String name);

}
