package com.example.igorg.woof.dao;

import android.content.Context;

import com.example.igorg.woof.modelo.Estabelecimento;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class EstabelecimentoDAO {

    public EstabelecimentoDAO(Context context){

    }

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference estabelecimentoReferencia = databaseReference.child("Estabelecimentos");

    public void insere (Estabelecimento estabelecimento){

        insereIdSeNecessario(estabelecimento);

    }

    private void insereIdSeNecessario(Estabelecimento estabelecimento) {

    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }

}
