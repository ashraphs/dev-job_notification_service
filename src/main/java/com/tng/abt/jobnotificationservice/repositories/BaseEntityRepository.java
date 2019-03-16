package com.tng.abt.jobnotificationservice.repositories;

import com.touchngo.abt.utils.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Override existing JPA Spring Data methods
 *
 * @param <T>
 * @author kent
 */
@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity>
        extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
}
