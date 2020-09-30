package com.example.igorg.woof.modelo;

//Classe modelo para Pet(animal)

import java.util.List;


public class Pet {

    private int id;
    private String usuario_id;
    private String pet_nome;
    private String tipo;
    private String raca;
    private String sexo;
    private String nascimento;
    private String castracao;
    private String vacina;
    private String observacoes;

    public static String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

   static  String image;



    private String foto_url;

    public Pet(int id, String pet_nome, String raca) {
        this.id = id;
        this.pet_nome = pet_nome;
        this.raca = raca;
    }

    public Pet(int id, String pet_nome, String raca,String foto_url) {
        this.id = id;
        this.pet_nome = pet_nome;
        this.raca = raca;
        this.foto_url = foto_url;
    }

    public Pet() {

    }

    public Pet(int id, String foto_url) {
        this.id = id;
        this.foto_url = foto_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getPet_nome() {
        return pet_nome;
    }

    public void setPet_nome(String pet_nome) {
        this.pet_nome = pet_nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCastracao() {
        return castracao;
    }

    public void setCastracao(String castracao) {
        this.castracao = castracao;
    }

    public String getVacina() {
        return vacina;
    }

    public void setVacina(String vacina) {
        this.vacina = vacina;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }

    private List<Pet> pets;



    private void initializeData(){

    }
}
