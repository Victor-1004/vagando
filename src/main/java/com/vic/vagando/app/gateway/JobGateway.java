package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;

import java.util.Optional;
import java.util.UUID;

public interface JobGateway {
    Job createJob(Job job);
    Optional<Job> getById(UUID id);
    JobSkills saveJobSkill(JobSkills jobSkills);
    PageModel<Job> getCompanyJobs(UUID companyId, int page, int size);
    PageModel<Job> findJobsNotAppliedByCandidateId(UUID candidateId, int page, int size);
    PageModel<Job> findJobs(int page, int size);
}
