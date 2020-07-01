package com.example.myapplication;

import com.google.firebase.database.Exclude;

public class Administrador {

    private String nomeAdministrador;
    private String emailAdministrador;
    private String senhaAdministrador;
    private String kyeUserAdministrador;

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getEmailAdministrador() {
        return emailAdministrador;
    }

    public void setEmailAdministrador(String emailAdministrador) {
        this.emailAdministrador = emailAdministrador;
    }
    @Exclude
    public String getSenhaAdministrador() {
        return senhaAdministrador;
    }

    public void setSenhaAdministrador(String senhaAdministrador) {
        this.senhaAdministrador = senhaAdministrador;
    }

    public String getKyeUserAdministrador() {
        return kyeUserAdministrador;
    }

    public void setKyeUserAdministrador(String kyeUserAdministrador) {
        this.kyeUserAdministrador = kyeUserAdministrador;
    }
}


