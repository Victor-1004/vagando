package com.vic.vagando.infrastructure.persistence.job;

import com.vic.vagando.infrastructure.entity.job.JobSkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobSkillsRepository extends JpaRepository<JobSkillsEntity, UUID> {
}
