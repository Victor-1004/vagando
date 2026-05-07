package com.vic.vagando.app.gateway;

import com.vic.vagando.app.domain.company.Company;

import java.util.Optional;

public interface CompanyGateway {
    Company save(Company company);
    Optional<Company> findByUserEmail(String email);
}
