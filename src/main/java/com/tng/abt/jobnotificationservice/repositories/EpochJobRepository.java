package com.tng.abt.jobnotificationservice.repositories;

import com.tng.abt.jobnotificationservice.entities.EpochJob;

public interface EpochJobRepository extends MasterEntityRepository<EpochJob> {

    EpochJob findAllByJobName(String name);

}
