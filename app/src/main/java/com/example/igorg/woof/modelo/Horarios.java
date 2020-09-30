package com.example.igorg.woof.modelo;

public class Horarios {

    String ID;
    String Tipo;
    String Horario;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String Horario) {
        this.Horario = Horario;
    }

    public Horarios(String ID, String Tipo, String Horario) {
        super();
        this.ID = ID;
        this.Tipo = Tipo;
        this.Horario = Horario;
    }


}



