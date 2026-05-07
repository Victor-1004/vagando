package com.vic.vagando.infrastructure.adapter;

import com.vic.vagando.app.domain.candidate.Candidate;
import com.vic.vagando.app.domain.candidate.CandidateSkills;
import com.vic.vagando.app.gateway.CandidateGateway;
import com.vic.vagando.infrastructure.adapter.mapper.CandidateMapper;
import com.vic.vagando.infrastructure.persistence.candidate.CandidateRepository;
import com.vic.vagando.infrastructure.persistence.candidate.CandidateSkillsRepository;

import java.util.Optional;
import java.util.UUID;

public class CandidateAdapter implements CandidateGateway {
    private final CandidateRepository candidateRepository;
    private final CandidateSkillsRepository candidateSkillsRepository;

    public CandidateAdapter(CandidateRepository candidateRepository, CandidateSkillsRepository candidateSkillsRepository) {
        this.candidateRepository = candidateRepository;
        this.candidateSkillsRepository = candidateSkillsRepository;
    }

    @Override
    public Candidate saveCandidate(Candidate candidate) {
       return CandidateMapper.toDomain(candidateRepository.save(CandidateMapper.toEntity(candidate)));
    }

    @Override
    public Optional<CandidateSkills> findBySkillId(UUID skillId) {
        return candidateSkillsRepository.findBySkillId(skillId).map(CandidateMapper::toDomain);
    }

    @Override
    public Optional<Candidate> findByUserEmail(String email) {
        return candidateRepository.findByUserEmail(email).map(CandidateMapper::toDomain);
    }

    @Override
    public CandidateSkills saveCandidateSkills(CandidateSkills candidateSkills) {
        return CandidateMapper.toDomain(candidateSkillsRepository.save(CandidateMapper.toEntity(candidateSkills)));
    }
}
