package com.example.igorg.woof.modelo;

import java.util.List;

public class Servicos {


    private int id;
    private String nome;
    private String descricao;
    private String valor;






    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDecricao() {
        return descricao; }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor; }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private List<Servicos> servicos;

}
