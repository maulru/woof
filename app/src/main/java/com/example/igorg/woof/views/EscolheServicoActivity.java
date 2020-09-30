package com.example.igorg.woof.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Vacina;

import java.util.List;

public class EscolheServicoActivity extends AppCompatActivity {

    Button visualizar,cadastrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhe_servico);

        visualizar = findViewById(R.id.btt_visualizar_servico);
        cadastrar = findViewById(R.id.btt_cadastro_servico);

       visualizar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), VisualizarServicosActivity.class));
           }
       });

       cadastrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(), CadastroServicoActivity.class));
           }
       });

    }
}
