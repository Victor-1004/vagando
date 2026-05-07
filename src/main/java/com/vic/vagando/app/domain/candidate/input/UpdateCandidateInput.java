package com.vic.vagando.app.domain.candidate.input;

import com.vic.vagando.app.domain.candidate.Candidate;

public class UpdateCandidateInput {
    private String nome;
    private String descricao;

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
