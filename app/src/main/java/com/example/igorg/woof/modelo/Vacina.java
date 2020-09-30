package com.example.igorg.woof.modelo;

public class Vacina {

    public int getID_Vacinas() {
        return ID_Vacinas;
    }

    public void setID_Vacinas(int ID_Vacinas) {
        this.ID_Vacinas = ID_Vacinas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData_Vacinacao() {
        return Data_Vacinacao;
    }

    public void setData_Vacinacao(String data_Vacinacao) {
        Data_Vacinacao = data_Vacinacao;
    }

    public String getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(String quantidade) {
        Quantidade = quantidade;
    }

    public int getID_Animal() {
        return ID_Animal;
    }

    public void setID_Animal(int ID_Animal) {
        this.ID_Animal = ID_Animal;
    }

    int ID_Vacinas;
    String tipo;
    String Data_Vacinacao;
    String Quantidade;
    int ID_Animal;
}
