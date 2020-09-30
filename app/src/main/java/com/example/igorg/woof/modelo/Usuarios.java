package com.example.igorg.woof.modelo;

public class Usuarios {

    public String getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(String ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    String ID_Usuario, Nome, Email, CPF, Telefone, Endereco;

    public Usuarios() {
    }



}
