package com.example.igorg.woof.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

public class AgendamentoTestActivity extends AppCompatActivity {

    EditText agendamento_data,agendamento_horario,agendamento_status,id_servico,id_animal,id_estabelecimento;
    Button confirmaAgendamento;

    private String server;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento_test);

        server = Servidor.getIp();

        usuario= SharedPrefManager.getInstance(this).getUser();

        agendamento_data = (EditText) findViewById(R.id.edit_agendamento_data);
        agendamento_horario = (EditText) findViewById(R.id.edit_agendamento_horario);
        agendamento_status = (EditText) findViewById(R.id.edit_agendamento_status);
        id_servico = (EditText) findViewById(R.id.edit_id_servico);
        id_animal = (EditText) findViewById(R.id.edit_id_animal);
        id_estabelecimento = (EditText) findViewById(R.id.edit_id_estabelecimento);

        confirmaAgendamento = (Button) findViewById(R.id.butt_confirma_agendamento);

        confirmaAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });


    }


    public void registrar(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_agendamento_servico.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        Toast.makeText(getApplicationContext(), "Sucesso!: " + response, Toast.LENGTH_SHORT).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro de resposta 2: "+ error, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("agendamento_data",agendamento_data.getText().toString());
                params.put("agendamento_status",agendamento_status.getText().toString());
                params.put("agendamento_horario",agendamento_horario.getText().toString());
                params.put("estabelecimento_id",agendamento_data.getText().toString());
                params.put("servico_id",agendamento_data.getText().toString());
                params.put("pet_id",agendamento_data.getText().toString());
                params.put("usuario_id",String.valueOf(usuario.getId()));


                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }



}
