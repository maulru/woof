package com.example.igorg.woof.dao;

import android.content.Context;


import com.example.igorg.woof.modelo.Pedido;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class PedidoDAO {

    public PedidoDAO(Context context){

    }

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference pedidoReferencia = databaseReference.child("Pedidos");

    public void insere (Pedido pedido){

        insereIdSeNecessario(pedido);

        pedidoReferencia.child(pedido.getId()).setValue(pedido);

    }

    private void insereIdSeNecessario(Pedido pedido) {
        if(pedido.getId() == null) {
            pedido.setId(geraUUID());
        }
    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }

}
