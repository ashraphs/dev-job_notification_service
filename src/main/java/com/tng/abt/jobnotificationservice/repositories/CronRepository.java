package com.tng.abt.jobnotificationservice.repositories;

import com.tng.abt.jobnotificationservice.entities.Cron;

public interface CronRepository extends MasterEntityRepository<Cron> {

    Cron findTopByName(String name);
}
