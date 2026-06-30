package com.vic.vagando.util;

import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.app.domain.user.UserRole;

import java.util.Random;
import java.util.UUID;

public class CompanyFactory {
    private final Random random =  new Random();
    public static Company createCompany(User user) {
        Company company = new Company();
        company.setId(UUID.randomUUID());
        company.setName("Company " + company.getId().toString().substring(0, 5));
        company.setDescription("Description " + company.getId().toString().substring(0, 5));
        company.setCnpj(String.format("%02d.%03d.%03d/%04d-%02d",
                new Random().nextInt(100),
                new Random().nextInt(1000),
                new Random().nextInt(1000),
                new Random().nextInt(10000),
                new Random().nextInt(100)));
        company.setUser(user);
        return company;
    }
    public static Company createCompany() {
        Company company = new Company();
        company.setId(UUID.randomUUID());
        company.setName("Company " + company.getId().toString().substring(0, 5));
        company.setDescription("Description " + company.getId().toString().substring(0, 5));
        company.setCnpj(String.format("%02d.%03d.%03d/%04d-%02d",
                new Random().nextInt(100),
                new Random().nextInt(1000),
                new Random().nextInt(1000),
                new Random().nextInt(10000),
                new Random().nextInt(100)));
        company.setUser(UserFactory.createUser(UserRole.COMPANY));
        return company;
    }

}
