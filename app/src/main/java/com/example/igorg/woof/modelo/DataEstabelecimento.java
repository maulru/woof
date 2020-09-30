package com.example.igorg.woof.modelo;

import java.util.ArrayList;


public class DataEstabelecimento {

    int Id;
    String nome;
    String telefone;
    String endereco;


    public String getName() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public int getId() {

        return Id;
    }

    public void setId(int Id1) {

        this.Id = Id1;
    }


    public String getTelefone() {

        return telefone;
    }

    public void setTelefone(String telefone1) {

        this.telefone = telefone1;
    }

    public String getEndereco() {

        return endereco;
    }

    public void setEndereco(String endereco1) {

        this.endereco = endereco1;
    }

}