package com.vic.vagando.app.interactor;

import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.CandidateSkills;
import com.vic.vagando.app.domain.company.Company;
import com.vic.vagando.app.domain.Skills;
import com.vic.vagando.app.domain.user.Register;
import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.app.domain.user.UserRole;
import com.vic.vagando.app.gateway.*;
import com.vic.vagando.app.util.CNPJ;
import com.vic.vagando.app.util.CPF;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserInteractor {

    private final UserGateway userGateway;
    private final CandidateGateway candidateGateway;
    private final CompanyGateway companyGateway;
    private final SkillsGateway skillsGateway;
    private final AppGateway appGateway;

    public UserInteractor(UserGateway userGateway, CandidateGateway candidateGateway, CompanyGateway companyGateway, SkillsGateway skillsGateway, AppGateway appGateway) {
        this.userGateway = userGateway;
        this.candidateGateway = candidateGateway;
        this.companyGateway = companyGateway;
        this.skillsGateway = skillsGateway;
        this.appGateway = appGateway;
    }

    @Transactional
    public void registerUser(Register register) {
        if(userGateway.findByEmail(register.getUser().getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User();
        user.setEmail(register.getUser().getEmail());
        user.setPassword(register.getUser().getPassword());
        if(register.getIsCompany() != null && register.getIsCompany()) {
            user.setRole(UserRole.COMPANY);
        } else {
            user.setRole(UserRole.CANDIDATE);
        }

        if(user.getRole() == null) {
            throw new IllegalArgumentException("User role must be specified");
        }
        if(register.getIdentificationNumber() == null || register.getIdentificationNumber().isEmpty()) {
            throw new IllegalArgumentException("Identification number must be provided");
        }
        User savedUser = userGateway.saveUser(user);
        if(user.getRole() == UserRole.CANDIDATE) {
            Candidate candidate = new Candidate();
            if(CPF.isCPF(register.getIdentificationNumber())){
                candidate.setCpf(register.getIdentificationNumber().replace(".", "").replace("-", ""));
            }else{
                throw new IllegalArgumentException("Invalid CPF format");
            }
            if(register.getCandidateName() == null || register.getCandidateName().isEmpty()) {
                throw new IllegalArgumentException("Candidate name must be provided");
            }
            candidate.setName(register.getCandidateName());
            candidate.setDescription(register.getCandidateDescription());
            candidate.setUser(savedUser);
            candidate.setCreatedAt(appGateway.getCurrentDateTime());
            if(register.getSkills() == null || register.getSkills().isEmpty() || register.getSkills().size() < 3) {
                throw new IllegalArgumentException("At least three skills must be provided for candidates");
            }
            Set<UUID> uniqueSkillIds = new HashSet<>(register.getSkills());
            if(uniqueSkillIds.size() < 3) {
                throw new IllegalArgumentException("At least three unique skills must be provided for candidates");
            }
            Candidate savedCandidate = candidateGateway.saveCandidate(candidate);

            Set<CandidateSkills> skillsSet = new HashSet<>();
            for(UUID skillId : uniqueSkillIds) {
                Skills skill = skillsGateway.findById(skillId).orElseThrow(() -> new IllegalArgumentException("Skill with ID " + skillId + " not found"));
                CandidateSkills candidateSkills = new CandidateSkills();
                candidateSkills.setCandidate(savedCandidate);
                candidateSkills.setSkill(skill);
                candidateGateway.saveCandidateSkills(candidateSkills);
                skillsSet.add(candidateSkills);
            }
            savedCandidate.setSkills(skillsSet);
        }
        if(user.getRole() == UserRole.COMPANY) {
            Company company = new Company();
            if(CNPJ.isCNPJ(register.getIdentificationNumber())){
                company.setCnpj(register.getIdentificationNumber().replace(".", "").replace("/", "").replace("-", ""));
            }else{
                throw new IllegalArgumentException("Invalid CNPJ format");
            }
            if(register.getCompanyName() == null || register.getCompanyName().isEmpty()) {
                throw new IllegalArgumentException("Company name must be provided");
            }
            company.setName(register.getCompanyName());
            company.setDescription(register.getCompanyDescription());
            company.setUser(savedUser);
            company.setCreatedAt(appGateway.getCurrentDateTime());
            companyGateway.save(company);
        }

    }

    public User findUserByEmail(String email) {
        return userGateway.findByEmail(email).orElse(null);
    }
}
