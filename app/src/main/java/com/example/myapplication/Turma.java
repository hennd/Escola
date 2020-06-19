package com.example.myapplication;

import java.util.List;

public class Turma {
    private String nomeTurma;
    private String keyUserTurma;
    private List<String> professores;
    private List<String> alunosTurma;

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    public String getKeyUserTurma() {
        return keyUserTurma;
    }

    public void setKeyUserTurma(String keyUserTurma) {
        this.keyUserTurma = keyUserTurma;
    }

    public List<String> getProfessores() {
        return professores;
    }

    public void setProfessores(List<String> professores) {
        this.professores = professores;
    }

    public List<String> getAlunosTurma() {
        return alunosTurma;
    }

    public void setAlunosTurma(List<String> alunosTurma) {
        this.alunosTurma = alunosTurma;
    }

    @Override
    public String toString() {
        return "Turma{" +
                "nomeTurma='" + nomeTurma + '\'' +
                ", keyUserTurma='" + keyUserTurma + '\'' +
                ", professores=" + professores +
                ", alunosTurma=" + alunosTurma +
                '}';
    }
}


