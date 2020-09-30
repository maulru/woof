package com.example.igorg.woof.dao;

import android.content.Context;

import com.example.igorg.woof.modelo.Pet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class PetDAO {

    public PetDAO(Context context) {

    }

    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference petReferencia = databaseReferencia.child("pets");

    public void insere(Pet pet){

        insereIdSeNecessario(pet);



    }
    private void insereIdSeNecessario(Pet pet) {

    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }
}



