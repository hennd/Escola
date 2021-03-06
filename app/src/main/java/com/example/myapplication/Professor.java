package com.example.myapplication;

import com.google.firebase.database.Exclude;

public class Professor {
    private String nomeProfessora;
    private String telefoneProfessora;
    private String cargoProfessora;
    private Integer cargoSpinnerProfessora;
    private String emailProfessora;
    private String senhaProfessora;
    private String keyUser;
    private String keyTurma;

    public String getKeyTurma() {
        return keyTurma;
    }

    public void setKeyTurma(String keyTurma) {
        this.keyTurma = keyTurma;
    }

    public Integer getCargoSpinnerProfessora() {
        return cargoSpinnerProfessora;
    }

    public void setCargoSpinnerProfessora(Integer cargoSpinnerProfessora) {
        this.cargoSpinnerProfessora = cargoSpinnerProfessora;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public String getNomeProfessora() {
        return nomeProfessora;
    }

    public void setNomeProfessora(String nomeProfessora) {
        this.nomeProfessora = nomeProfessora;
    }

    public String getTelefoneProfessora() {
        return telefoneProfessora;
    }

    public void setTelefoneProfessora(String telefoneProfessora) {
        this.telefoneProfessora = telefoneProfessora;
    }

    public String getCargoProfessora() {
        return cargoProfessora;
    }

    public void setCargoProfessora(String cargoProfessora) {
        this.cargoProfessora = cargoProfessora;
    }

    public String getEmailProfessora() {
        return emailProfessora;
    }

    public void setEmailProfessora(String emailProfessora) {
        this.emailProfessora = emailProfessora;
    }

    public String getSenhaProfessora() {
        return senhaProfessora;
    }

    public void setSenhaProfessora(String senhaProfessora) {
        this.senhaProfessora = senhaProfessora;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "nomeProfessora='" + nomeProfessora + '\'' +
                ", telefoneProfessora='" + telefoneProfessora + '\'' +
                ", cargoProfessora='" + cargoProfessora + '\'' +
                ", emailProfessora='" + emailProfessora + '\'' +
                ", senhaProfessora='" + senhaProfessora + '\'' +
                ", keyUser='" + keyUser + '\'' +
                '}';
    }
}

