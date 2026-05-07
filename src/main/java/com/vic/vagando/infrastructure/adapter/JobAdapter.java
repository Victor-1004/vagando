package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;
import com.vic.vagando.app.gateway.JobGateway;
import com.vic.vagando.infrastructure.adapter.mapper.JobMapper;
import com.vic.vagando.infrastructure.persistence.job.JobRepository;
import com.vic.vagando.infrastructure.persistence.job.JobSkillsRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JobAdapter implements JobGateway {
    private final JobRepository jobRepository;
    private final JobSkillsRepository jobSkillsRepository;

    public JobAdapter(JobRepository jobRepository, JobSkillsRepository jobSkillsRepository) {
        this.jobRepository = jobRepository;
        this.jobSkillsRepository = jobSkillsRepository;
    }

    @Override
    public Job createJob(Job job) {
        return JobMapper.toDomain(jobRepository.save(JobMapper.toEntity(job)));
    }

    @Override
    public Optional<Job> getById(UUID id) {
        return jobRepository.findById(id).map(JobMapper::toDomain);
    }

    @Override
    public JobSkills saveJobSkill(JobSkills jobSkills) {
        return JobMapper.toDomainSkillLink(jobSkillsRepository.save(JobMapper.toEntitySkillLink(jobSkills)));
    }

    @Override
    public List<Job> getCompanyJobs(UUID companyId) {
        return jobRepository.findByCompanyId(companyId).stream().map(JobMapper::toDomain).toList();
    }
}
