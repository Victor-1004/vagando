package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.gateway.CompanyGateway;
import com.vic.vagando.infrastructure.adapter.mapper.CompanyMapper;
import com.vic.vagando.infrastructure.persistence.CompanyRepository;

import java.util.Optional;

public class CompanyAdapter implements CompanyGateway {
    private final CompanyRepository companyRepository;

    public CompanyAdapter(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        return CompanyMapper.toDomain(companyRepository.save(CompanyMapper.toEntity(company)));
    }

    @Override
    public Optional<Company> findByUserEmail(String email) {
        return companyRepository.findByUserEmail(email).map(CompanyMapper::toDomain);
    }

    @Override
    public Optional<Company> findByCNPJ(String cnpj) {
        return companyRepository.findByCNPJ(cnpj).map(CompanyMapper::toDomain);
    }
}
