package com.example.igorg.woof.dao;

import android.content.Context;

import com.example.igorg.woof.modelo.Consulta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ConsultaDAO {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference consultaReferencia = databaseReferencia.child("Consultas");

    public ConsultaDAO(Context context) {
    }

    public void insere(Consulta consulta, String uid) {

        consultaReferencia.child(uid).setValue(consulta);

    }


}
