package com.vic.vagando.app.domain.candidate.input;

import com.vic.vagando.app.domain.candidate.Candidate;

import java.util.List;
import java.util.UUID;

public class UpdateCandidateInput {
    private String nome;
    private String descricao;
    private List<UUID> skills;

    public List<UUID> getSkills() {
        return skills;
    }

    public void setSkills(List<UUID> skills) {
        this.skills = skills;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Candidate toDomain(){
        Candidate candidate = new Candidate();
        candidate.setName(this.nome);
        candidate.setDescription(this.descricao);
        return candidate;
    }
}
