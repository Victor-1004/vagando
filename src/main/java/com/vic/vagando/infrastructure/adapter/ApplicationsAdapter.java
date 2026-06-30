package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.applications.Applications;
import com.vic.vagando.app.gateway.ApplicationsGateway;
import com.vic.vagando.infrastructure.adapter.mapper.ApplicationsMapper;
import com.vic.vagando.infrastructure.adapter.mapper.PageRepositoryMapper;
import com.vic.vagando.infrastructure.persistence.ApplicationsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ApplicationsAdapter implements ApplicationsGateway {
    private final ApplicationsRepository repository;

    public ApplicationsAdapter(ApplicationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Applications save(Applications applications) {
        return ApplicationsMapper.toDomain(repository.save(ApplicationsMapper.toEntity(applications)));
    }

    @Override
    public List<Applications> listByJobId(UUID jobId) {
        return repository.listByJobId(jobId).stream().map(ApplicationsMapper::toDomain).toList();
    }


    @Override
    public PageModel<Applications> findByJobId(UUID jobId, int page, int size) {
        return PageRepositoryMapper.toDomain(repository.findByJobId(jobId, PageRequest.of(page, size)).map(ApplicationsMapper::toDomain));
    }

    @Override
    public Optional<Applications> findById(UUID id) {
        return repository.findById(id).map(ApplicationsMapper::toDomain);
    }

    @Override
    public PageModel<Applications> findApplicationsToCompanyJobs(UUID companyId, String job, String status, int page, int size) {
        return PageRepositoryMapper.toDomain(repository.findApplicationsToCompanyJobs(
                companyId,
                job,
                status,
                PageRequest.of(page, size)
        )).map(ApplicationsMapper::toDomain);
    }

    @Override
    public int countApplicationsLastDayByCompanyId(UUID companyId) {
        LocalDateTime since = LocalDateTime.now().minusDays(1);
        return repository.countApplicationsLastDayByCompanyId(companyId, since);
    }

    @Override
    public int countApplicationsByCompanyId(UUID companyId) {
        return repository.countApplicationsByCompanyId(companyId);
    }

    @Override
    public int countPendingApplicationsByCompanyId(UUID companyId) {
        return repository.countPendingApplicationsByCompanyId(companyId);
    }

    @Override
    public int countAprovedApplicationsByCompanyId(UUID companyId) {
        return repository.countAprovedApplicationsByCompanyId(companyId);
    }

    @Override
    public List<Applications> listByCompanyId(UUID companyId) {
        Pageable pageable = PageRequest.of(0, 4);
        return repository.listByCompanyId(companyId, pageable).stream().map(ApplicationsMapper::toDomain).toList();
    }

    @Override
    public PageModel<Applications> findApplicationsByCandidateId(UUID candidateId, int page, int size) {
        return PageRepositoryMapper.toDomain(repository.findApplicationsByCandidateId(candidateId, PageRequest.of(page, size)).map(ApplicationsMapper::toDomain));
    }

    @Override
    public boolean existsByJobAndCandidateId(UUID jobId, UUID candidateId) {
        return repository.existsByJobAndCandidateId(jobId, candidateId);
    }


}
