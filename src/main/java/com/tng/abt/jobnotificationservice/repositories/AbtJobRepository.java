package com.tng.abt.jobnotificationservice.repositories;

import com.tng.abt.jobnotificationservice.entities.EpochJob;

public interface AbtJobRepository extends MasterEntityRepository<EpochJob> {

    EpochJob findAllByJobName(String name);

}
