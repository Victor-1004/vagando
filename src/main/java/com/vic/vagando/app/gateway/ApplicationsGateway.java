package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.applications.Applications;
import com.vic.vagando.app.domain.ouput.ApplicationsCompanyOutput;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationsGateway {
    Applications save(Applications applications);
    List<Applications> listByJobId(UUID jobId);
    PageModel<Applications> findByJobId(UUID jobId, int page, int size);
    Optional<Applications> findById(UUID id);
    PageModel<Applications> findApplicationsToCompanyJobs(UUID companyId, String job, String status, int page, int size);
    int countApplicationsLastDayByCompanyId(UUID companyId);
    int countApplicationsByCompanyId(UUID companyId);
    int countPendingApplicationsByCompanyId(UUID companyId);
    int countAprovedApplicationsByCompanyId(UUID companyId);
    List<Applications> listByCompanyId(UUID companyId);
    PageModel<Applications> findApplicationsByCandidateId(UUID candidateId, int page, int size);
    boolean existsByJobAndCandidateId(UUID jobId, UUID candidateId);
}
