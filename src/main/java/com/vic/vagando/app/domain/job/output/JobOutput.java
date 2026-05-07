package com.vic.vagando.app.domain.job.output;

import com.vic.vagando.app.domain.job.Job;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class JobOutput {
    private JobCompanyOutput company;
    private Set<JobSkillOutput> skills;
    private String title;
    private String description;
    private String requirements;
    private LocalDateTime createdAt;

    public JobCompanyOutput getCompany() {
        return company;
    }

    public void setCompany(JobCompanyOutput company) {
        this.company = company;
    }

    public Set<JobSkillOutput> getSkills() {
        return skills;
    }

    public void setSkills(Set<JobSkillOutput> skills) {
        this.skills = skills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class JobCompanyOutput{
        private String name;
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class JobSkillOutput{
        private String name;
        private UUID id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }
    }

    public JobOutput fromDomain(Job job){
        JobOutput output = new JobOutput();
        output.setTitle(job.getTitle());
        output.setDescription(job.getDescription());
        output.setRequirements(job.getRequirements());
        output.setCreatedAt(job.getCreatedAt());
        JobCompanyOutput companyOutput = new JobCompanyOutput();
        companyOutput.setName(job.getCompany().getName());
        companyOutput.setDescription(job.getCompany().getDescription());
        output.setCompany(companyOutput);
        Set<JobSkillOutput> skillOutputs = job.getSkills().stream().map(js -> {
            JobSkillOutput skillOutput = new JobSkillOutput();
            skillOutput.setId(js.getSkill().getId());
            skillOutput.setName(js.getSkill().getName());
            return skillOutput;
        }).collect(java.util.stream.Collectors.toSet());
        output.setSkills(skillOutputs);
        return output;
    }
}
