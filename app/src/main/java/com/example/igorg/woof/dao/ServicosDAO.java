package com.example.igorg.woof.dao;


import android.content.Context;

import com.example.igorg.woof.modelo.Consulta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ServicosDAO {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference servicosReferencia = databaseReferencia.child("Servicos");

    public ServicosDAO(Context context) {
    }

    public void insere(Consulta consulta, String uid) {

        servicosReferencia.child(uid).setValue(consulta);

    }


}
