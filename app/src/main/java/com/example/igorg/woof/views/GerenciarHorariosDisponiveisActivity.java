package com.example.igorg.woof.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.igorg.peteazyv20.R;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoTipo;

public class GerenciarHorariosDisponiveisActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar);


        Button Disponiveis = (Button) findViewById(R.id.disponiveis);
        Disponiveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_horarios_disponiveis = new Intent(GerenciarHorariosDisponiveisActivity.this,
                        HorariosDisponiveisActivity.class);
                startActivity(intent_horarios_disponiveis);
            }
        });

        Button Cadastrar = (Button) findViewById(R.id.cadastrar);
        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estabelecimentoTipo.equals("Petshop")){
                    Intent intentAjude = new Intent(GerenciarHorariosDisponiveisActivity.this,
                            RegistrarHorarioPetshop.class);
                    startActivity(intentAjude);
                }else{
                    Intent intentAjude = new Intent(GerenciarHorariosDisponiveisActivity.this,
                            RegistrarHorarioActivity.class);
                    startActivity(intentAjude);
                }
            }
        });
    }



}

