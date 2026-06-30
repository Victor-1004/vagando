package com.vic.vagando.app;

import com.vic.vagando.app.domain.PageModel;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.company.input.UpdateCompanyInput;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.input.CompanyJobInput;
import com.vic.vagando.app.domain.job.output.JobOutput;
import com.vic.vagando.app.gateway.AppGateway;
import com.vic.vagando.app.gateway.CompanyGateway;
import com.vic.vagando.app.gateway.JobGateway;
import com.vic.vagando.app.gateway.SkillsGateway;
import com.vic.vagando.app.interactor.CompanyInteractor;
import com.vic.vagando.util.CompanyFactory;
import com.vic.vagando.util.JobFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@ExtendWith(MockitoExtension.class)
public class CompanyInteractorTest {
    @Mock
    private CompanyGateway companyGateway;

    @Mock
    private AppGateway appGateway;

    @Mock
    private JobGateway jobGateway;

    @Mock
    private SkillsGateway skillsGateway;

    @InjectMocks
    private CompanyInteractor companyInteractor;


    Company getCompany(){
        String email = "test@test";
        when(appGateway.getLoggedUserEmail()).thenReturn(email);
        Company company = CompanyFactory.createCompany();
        when(companyGateway.findByUserEmail(email)).thenReturn(Optional.of(company));
        return company;
    }

    @Test
    void createCompany_Test() {
        when(appGateway.getCurrentDateTime()).thenReturn(LocalDateTime.now());
        getCompany();
        List<Skills> skills = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Skills skill = new Skills();
            skill.setId(java.util.UUID.randomUUID());
            skill.setName("Skill " + i);
            skills.add(skill);
            when(skillsGateway.findById(skill.getId())).thenReturn(Optional.of(skill));
        }
        CompanyJobInput input = new CompanyJobInput();
        input.setSkills(skills.stream().map(Skills::getId).toList());
        input.setDescription("Job description");
        input.setRequirements("Job requirements");
        input.setTitle("Job title");

        when(jobGateway.createJob(any()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        JobOutput job = companyInteractor.createJob(input);

        assertNotNull(job);
    }

    @Test
    void getJobs_Test(){
        Company company = getCompany();
        List<Job> jobs = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Job job = JobFactory.createJob(company);
            jobs.add(job);
        }
        PageModel<Job> jobPage = new PageModel<>(jobs, 0, 10, jobs.size(), 1);
        when(jobGateway.getCompanyJobs(company.getId(), 0, 10, null)).thenReturn(jobPage);
        PageModel<JobOutput> jobOutputs = companyInteractor.getJobs(0, 10, null);
        assertNotNull(jobOutputs);
        org.junit.jupiter.api.Assertions.assertEquals(5, jobOutputs.getContent().size());
    }

    @Test
    void update_Test(){
        getCompany();
        UpdateCompanyInput input = new UpdateCompanyInput();
        input.setNome("Updated company");
        input.setDescricao("Updated description");
        when(companyGateway.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Company updatedCompany = companyInteractor.update(input);
        assertNotNull(updatedCompany);
        assertEquals("Failed", "Updated company", updatedCompany.getName());
        assertEquals("Failed", "Updated description", updatedCompany.getDescription());
    }


}
