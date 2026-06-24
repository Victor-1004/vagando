package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;
import com.vic.vagando.app.gateway.JobGateway;
import com.vic.vagando.infrastructure.adapter.mapper.JobMapper;
import com.vic.vagando.infrastructure.adapter.mapper.PageRepositoryMapper;
import com.vic.vagando.infrastructure.persistence.job.JobRepository;
import com.vic.vagando.infrastructure.persistence.job.JobSkillsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;

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
        return jobRepository.findByIdFetchingSkills(id).map(JobMapper::toDomain);
    }

    @Override
    public JobSkills saveJobSkill(JobSkills jobSkills) {
        return JobMapper.toDomainSkillLink(jobSkillsRepository.save(JobMapper.toEntitySkillLink(jobSkills)));
    }

    @Override
    @Transactional
    public PageModel<Job> getCompanyJobs(UUID companyId, int page, int size) {
        return PageRepositoryMapper.toDomain(jobRepository.findByCompanyId(companyId, PageRequest.of(page, size)).map(JobMapper::toDomain));
    }

    @Override
    public PageModel<Job> findJobsNotAppliedByCandidateId(UUID candidateId, int page, int size) {
        return PageRepositoryMapper.toDomain(jobRepository.findJobsNotAppliedByCandidateId(candidateId, PageRequest.of(page, size)).map(JobMapper::toDomain));
    }

    @Override
    public PageModel<Job> findJobs(int page, int size) {
        return PageRepositoryMapper.toDomain(jobRepository.find(PageRequest.of(page, size)).map(JobMapper::toDomain));
    }

    @Override
    public Optional<Job> findById(UUID id) {
        return jobRepository.findByIdFetchingSkills(id).map(JobMapper::toDomain);
    }

    @Override
    public PageModel<Job> findJobsAppliedByCandidateId(UUID candidateId, int page, int size) {
        return PageRepositoryMapper.toDomain(jobRepository.findJobsAppliedByCandidateId(candidateId, PageRequest.of(page, size)).map(JobMapper::toDomain));
    }
}
