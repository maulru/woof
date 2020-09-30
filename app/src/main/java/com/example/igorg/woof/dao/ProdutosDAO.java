package com.example.igorg.woof.dao;

import android.content.Context;


import com.example.igorg.woof.modelo.Produtos;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class ProdutosDAO {

    public ProdutosDAO(Context context){

    }

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference produtoReferencia = databaseReference.child("Produtos");

    public void insere (Produtos produtos){

        insereIdSeNecessario(produtos);

        produtoReferencia.child(produtos.getId()).setValue(produtos);

    }

    private void insereIdSeNecessario(Produtos produtos) {
        if(produtos.getId() == null) {
            produtos.setId(geraUUID());
        }
    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }

}
