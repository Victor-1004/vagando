package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobGateway {
    Job createJob(Job job);
    Optional<Job> getById(UUID id);
    JobSkills saveJobSkill(JobSkills jobSkills);
    List<Job> getCompanyJobs(UUID companyId);
}
