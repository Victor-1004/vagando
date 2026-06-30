package com.vic.vagando.app.domain.ouput;

import com.vic.vagando.app.domain.job.output.JobOutput;

import java.util.List;

public class Dashboard {
    public List<JobOutput> vagasEmDestaque;
    public CardInfo cardInfo;
    public List<ApplicationsCompanyOutput> candidaturasRecentes;


    public Dashboard(List<JobOutput> vagasEmDestaque, CardInfo cardInfo) {
        this.vagasEmDestaque = vagasEmDestaque;
        this.cardInfo = cardInfo;
    }

    public Dashboard() {
    }

    public List<ApplicationsCompanyOutput> getCandidaturasRecentes() {
        return candidaturasRecentes;
    }

    public void setCandidaturasRecentes(List<ApplicationsCompanyOutput> candidaturasRecentes) {
        this.candidaturasRecentes = candidaturasRecentes;
    }

    public List<JobOutput> getVagasEmDestaque() {
        return vagasEmDestaque;
    }

    public void setVagasEmDestaque(List<JobOutput> vagasEmDestaque) {
        this.vagasEmDestaque = vagasEmDestaque;
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public static class CardInfo{
        private int vagasAtivas;
        private int candidaturasTotais;
        private int candidaturasTotalUltimoDia;
        private int candidaturasPendentes;
        private int candidaturasAprovadas;

        public int getCandidaturasTotais() {
            return candidaturasTotais;
        }

        public void setCandidaturasTotais(int candidaturasTotais) {
            this.candidaturasTotais = candidaturasTotais;
        }

        public int getVagasAtivas() {
            return vagasAtivas;
        }

        public void setVagasAtivas(int vagasAtivas) {
            this.vagasAtivas = vagasAtivas;
        }

        public int getCandidaturasTotalUltimoDia() {
            return candidaturasTotalUltimoDia;
        }

        public void setCandidaturasTotalUltimoDia(int candidaturasTotalUltimoDia) {
            this.candidaturasTotalUltimoDia = candidaturasTotalUltimoDia;
        }

        public int getCandidaturasPendentes() {
            return candidaturasPendentes;
        }

        public void setCandidaturasPendentes(int candidaturasPendentes) {
            this.candidaturasPendentes = candidaturasPendentes;
        }

        public int getCandidaturasAprovadas() {
            return candidaturasAprovadas;
        }

        public void setCandidaturasAprovadas(int candidaturasAprovadas) {
            this.candidaturasAprovadas = candidaturasAprovadas;
        }
    }
}

