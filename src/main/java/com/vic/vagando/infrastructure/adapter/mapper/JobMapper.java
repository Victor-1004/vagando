package com.vic.vagando.infrastructure.adapter.mapper;

import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;
import com.vic.vagando.infrastructure.entity.job.JobEntity;
import com.vic.vagando.infrastructure.entity.job.JobSkillsEntity;

import java.util.stream.Collectors;

public class JobMapper {
    public static Job toDomainWithoutSkillsAndCompany(JobEntity entity) {
        if (entity == null) {
            return null;
        }
       Job job = new Job();
        job.setId(entity.getId());
        job.setTitle(entity.getTitle());
        job.setDescription(entity.getDescription());
        job.setRequirements(entity.getRequirements());
        job.setCreatedAt(entity.getCreatedAt());
        job.setActive(entity.getActive());
        return job;
    }
    public static JobEntity toEntityWithoutSkillsAndCompany(Job job) {
        if (job == null) {
            return null;
        }
        JobEntity entity = new JobEntity();
        entity.setId(job.getId());
        entity.setTitle(job.getTitle());
        entity.setDescription(job.getDescription());
        entity.setRequirements(job.getRequirements());
        entity.setCreatedAt(job.getCreatedAt());
        entity.setActive(job.getActive());
        return entity;
    }
    public static Job toDomain(JobEntity entity) {
        Job job = toDomainWithoutSkillsAndCompany(entity);
        if(entity != null && entity.getSkills() != null) {
            job.setSkills(entity.getSkills().stream().map(JobMapper::toDomainSkillLink).collect(Collectors.toSet()));
        }
        job.setCompany(CompanyMapper.toDomainWithoutJob(entity.getCompany()));
        return job;
    }
    public static JobEntity toEntity(Job job) {
        JobEntity entity = toEntityWithoutSkillsAndCompany(job);
        if(job != null && job.getSkills() != null) {
            entity.setSkills(job.getSkills().stream().map(skill -> toEntitySkillLink(skill, entity)).collect(Collectors.toSet()));
        }
        entity.setCompany(CompanyMapper.toEntityWithoutJob(job.getCompany()));
        return entity;
    }

    public static JobSkills toDomainSkillLink(JobSkillsEntity entity) {
        if (entity == null) {
            return null;
        }
        JobSkills jobSkills = new JobSkills();
        jobSkills.setId(entity.getId());
        jobSkills.setJob(toDomainWithoutSkillsAndCompany(entity.getJob()));
        jobSkills.setSkill(SkillsMapper.toDomain(entity.getSkill()));
        return jobSkills;
    }

    public static JobSkillsEntity toEntitySkillLink(JobSkills jobSkills) {
        if (jobSkills == null) {
            return null;
        }
        return toEntitySkillLink(jobSkills, toEntityWithoutSkillsAndCompany(jobSkills.getJob()));
    }

    public static JobSkillsEntity toEntitySkillLink(JobSkills jobSkills, JobEntity jobEntity) {
        if (jobSkills == null) {
            return null;
        }
        JobSkillsEntity entity = new JobSkillsEntity();
        entity.setId(jobSkills.getId());
        entity.setJob(jobEntity);
        entity.setSkill(SkillsMapper.toEntity(jobSkills.getSkill()));
        return entity;
    }
}
