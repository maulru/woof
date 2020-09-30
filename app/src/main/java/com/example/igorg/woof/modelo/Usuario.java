package com.example.igorg.woof.modelo;

import java.io.Serializable;

//Classe modelo para usuário

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String CPF;
    private String nascimento;
    private String Senha;
    private String confirmaSenha;
    private String telefone;
    private String endereco;
    private String caminhoFoto;
    private String sexo;

    private int media;

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }



    public Usuario(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Usuario(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;

    }

    public Usuario(String id, String nome, String email,int media) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.media = media;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }




}
