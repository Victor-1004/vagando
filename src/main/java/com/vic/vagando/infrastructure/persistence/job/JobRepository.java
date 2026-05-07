package com.vic.vagando.infrastructure.persistence.job;

import com.vic.vagando.infrastructure.entity.job.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    @Query("SELECT DISTINCT j FROM JobEntity j LEFT JOIN FETCH j.skills sk LEFT JOIN FETCH sk.skill s LEFT JOIN FETCH j.company c WHERE j.company.id = :companyId")
    List<JobEntity> findByCompanyId(UUID companyId);
}
