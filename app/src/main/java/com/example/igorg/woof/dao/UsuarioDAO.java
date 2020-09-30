package com.example.igorg.woof.dao;

import android.content.Context;

import com.example.igorg.woof.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class UsuarioDAO {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = databaseReferencia.child("usuarios");

    public UsuarioDAO(Context context) {
    }

    public void insere(Usuario usuario, String uid) {

        usuarioReferencia.child(uid).setValue(usuario);

    }


}
