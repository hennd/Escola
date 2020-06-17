package com.example.myapplication;

import com.google.firebase.database.Exclude;

public class Aluno {

    private String nomeAluno;
    private String nomeMaeAluno;
    private String nomePaiAluno;
    private String telefoneAluno;
    private String emailAluno;
    private String password;
    private String keyUser;

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }



    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeMaeAluno() {
        return nomeMaeAluno;
    }

    public void setNomeMaeAluno(String nomeMaeAluno) {
        this.nomeMaeAluno = nomeMaeAluno;
    }

    public String getNomePaiAluno() {
        return nomePaiAluno;
    }

    public void setNomePaiAluno(String nomePaiAluno) {
        this.nomePaiAluno = nomePaiAluno;
    }

    public String getTelefoneAluno() {
        return telefoneAluno;
    }

    public void setTelefoneAluno(String telefoneAluno) {
        this.telefoneAluno = telefoneAluno;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "nomeAluno='" + nomeAluno + '\'' +
                ", nomeMaeAluno='" + nomeMaeAluno + '\'' +
                ", nomePaiAluno='" + nomePaiAluno + '\'' +
                ", telefoneAluno='" + telefoneAluno + '\'' +
                ", emailAluno='" + emailAluno + '\'' +
                ", password='" + password + '\'' +
                ", keyUser='" + keyUser + '\'' +
                '}';
    }
}
