package com.example.igorg.woof.dao;

import android.content.Context;


import com.example.igorg.woof.modelo.Funcionario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class FuncionarioDAO {

    public FuncionarioDAO(Context context){

    }

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference funcionarioReferencia = databaseReference.child("Funcionarios");

    public void insere (Funcionario funcionario){

        insereIdSeNecessario(funcionario);

        funcionarioReferencia.child(funcionario.getId()).setValue(funcionario);

    }

    private void insereIdSeNecessario(Funcionario funcionario) {
        if(funcionario.getId() == null) {
            funcionario.setId(geraUUID());
        }
    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }

}
