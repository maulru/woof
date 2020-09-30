package com.example.igorg.woof.modelo;

public class Estabelecimento {

    public String getEstabelecimento_id() {
        return estabelecimento_id;
    }

    public void setEstabelecimento_id(String estabelecimento_id) {
        this.estabelecimento_id = estabelecimento_id;
    }

    public String getEstabelecimento_nome() {
        return estabelecimento_nome;
    }

    public void setEstabelecimento_nome(String estabelecimento_nome) {
        this.estabelecimento_nome = estabelecimento_nome;
    }

    public String getEstabelecimento_telefone() {
        return estabelecimento_telefone;
    }

    public void setEstabelecimento_telefone(String estabelecimento_telefone) {
        this.estabelecimento_telefone = estabelecimento_telefone;
    }

    public String getEstabelecimento_cnpj() {
        return estabelecimento_cnpj;
    }

    public void setEstabelecimento_cnpj(String estabelecimento_cnpj) {
        this.estabelecimento_cnpj = estabelecimento_cnpj;
    }

    public String getEstabelecimento_endereco() {
        return estabelecimento_endereco;
    }

    public void setEstabelecimento_endereco(String estabelecimento_endereco) {
        this.estabelecimento_endereco = estabelecimento_endereco;
    }

    public String getEstabelecimento_numero() {
        return estabelecimento_numero;
    }

    public void setEstabelecimento_numero(String estabelecimento_numero) {
        this.estabelecimento_numero = estabelecimento_numero;
    }

    public String getEstabelecimento_complemento() {
        return estabelecimento_complemento;
    }

    public void setEstabelecimento_complemento(String estabelecimento_complemento) {
        this.estabelecimento_complemento = estabelecimento_complemento;
    }

    public String getEstabelecimento_email() {
        return estabelecimento_email;
    }

    public void setEstabelecimento_email(String estabelecimento_email) {
        this.estabelecimento_email = estabelecimento_email;
    }

    public String getEstabelecimento_observacoes() {
        return estabelecimento_observacoes;
    }

    public void setEstabelecimento_observacoes(String estabelecimento_observacoes) {
        this.estabelecimento_observacoes = estabelecimento_observacoes;
    }

    public String getEstabelecimento_tipo() {
        return estabelecimento_tipo;
    }

    public void setEstabelecimento_tipo(String estabelecimento_tipo) {
        this.estabelecimento_tipo = estabelecimento_tipo;
    }

    public Estabelecimento(String estabelecimento_id, String estabelecimento_nome) {
        this.estabelecimento_id = estabelecimento_id;
        this.estabelecimento_nome = estabelecimento_nome;
    }

    String estabelecimento_id;
    String estabelecimento_nome;
    String estabelecimento_telefone;
    String estabelecimento_cnpj;
    String estabelecimento_endereco;
    String estabelecimento_numero;
    String estabelecimento_complemento;
    String estabelecimento_email;
    String estabelecimento_observacoes;
    String estabelecimento_tipo;

    public String getEstabelecimento_id_usuario() {
        return estabelecimento_id_usuario;
    }

    public void setEstabelecimento_id_usuario(String estabelecimento_id_usuario) {
        this.estabelecimento_id_usuario = estabelecimento_id_usuario;
    }

    String estabelecimento_id_usuario;

    public Estabelecimento() {

    }



}
