package com.tng.abt.jobnotificationservice.repositories;

import com.tng.abt.jobnotificationservice.entities.EpochJob;

import java.util.List;

public interface EpochJobRepository extends MasterEntityRepository<com.tng.abt.jobnotificationservice.entities.EpochJob> {

    EpochJob findAllByJobName(String name);

    List<EpochJob> findAllByDeletedIsFalse();

}
