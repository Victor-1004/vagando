package com.vic.vagando.util;

import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.job.Job;
import com.vic.vagando.app.domain.job.JobSkills;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class JobFactory {
    private static final Random random = new Random();

    public static Job createJob(Company company){
        Job job = new Job();
        UUID jobId = UUID.randomUUID();
        job.setId(jobId);
        job.setCompany(company);
        job.setCreatedAt(LocalDateTime.now());
        Set<JobSkills> skillsSet = new HashSet<>();
        for (int i = 0; i < random.nextInt(10); i++) {
            Skills skills = new Skills();
            skills.setId(UUID.randomUUID());
            skills.setName("Skill " + skills.getId().toString().substring(0, 5));
            JobSkills jobSkills = new JobSkills();
            jobSkills.setSkill(skills);
            jobSkills.setJob(job);
            skillsSet.add(jobSkills);
        }
        job.setSkills(skillsSet);
        job.setDescription("Job description " + jobId.toString().substring(0, 5));
        job.setTitle("Job title " + jobId.toString().substring(0, 5));
        job.setRequirements("Job requirements " + jobId.toString().substring(0, 5));
        return job;
    }

    public static Job createJob(Company company, Set<Skills> skills){
        Job job = new Job();
        UUID jobId = UUID.randomUUID();
        job.setId(jobId);
        job.setCompany(company);
        job.setCreatedAt(LocalDateTime.now());
        Set<JobSkills> skillsSet = new HashSet<>();
        for(Skills skill : skills){
            JobSkills jobSkills = new JobSkills();
            jobSkills.setSkill(skill);
            jobSkills.setJob(job);
            skillsSet.add(jobSkills);
        }
        job.setSkills(skillsSet);
        job.setDescription("Job description " + jobId.toString().substring(0, 5));
        job.setTitle("Job title " + jobId.toString().substring(0, 5));
        job.setRequirements("Job requirements " + jobId.toString().substring(0, 5));
        return job;
    }
}
