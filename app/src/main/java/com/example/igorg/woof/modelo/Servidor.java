package com.example.igorg.woof.modelo;

import android.content.SharedPreferences;

public class Servidor {

    static String ip="192.168.43.46";
    static String porta;
    public static SharedPreferences preferences;

    public static int estabelecimentoId;
    public static String estabelecimentoNome;
    public static String estabelecimentoEndereco;
    public static String estabelecimentoTipo;

    public static int usuario_rede;

    public static int petId ;
    public static String petNome;

    public static int servicoId;
    public static String servicoNome;

    public static int vacinaId;
    public static String vacinaTipo;

    public static String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public static String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }


}
