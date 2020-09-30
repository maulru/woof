package com.example.igorg.woof.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class ConfirmarAgendamentoActivity  extends AppCompatActivity {


    int id_horario = 13;

    TextView id_wtf;

    String servidor,usuario_id,servico;

    Usuario usuario;

    ProgressDialog progressDialog;


    TextView dia, nome_estabelecimento,horario, endereco,observacao, tipo, pet, id,horario_id,id_estabelecimeto,id_pet;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmaragendamento);


        Button agendar = (Button) findViewById(R.id.confirmar);
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar_usuario(v);
            }
        });

        servidor = Servidor.getIp();
        usuario= SharedPrefManager.getInstance(this).getUser();

        dia = (TextView)findViewById(R.id.dia);
        dia.setText(getIntent().getStringExtra("Data"));

        usuario_id = getIntent().getStringExtra("id_usuario");

        nome_estabelecimento = (TextView)findViewById(R.id.nome_estabelecimento);
        nome_estabelecimento.setText(getIntent().getStringExtra("Nome"));

        servico = getIntent().getStringExtra("servico");
        horario = (TextView)findViewById(R.id.horario);
        horario.setText(getIntent().getStringExtra("horario"));


        endereco = (TextView)findViewById(R.id.endereco);
        endereco.setText(getIntent().getStringExtra("Endereco"));


        observacao = (TextView)findViewById(R.id.observacao);
        observacao.setText(getIntent().getStringExtra("Observacao"));



        pet= (TextView)findViewById(R.id.pet);
        pet.setText(getIntent().getStringExtra("pet"));

        tipo = (TextView)findViewById(R.id.evento);
        tipo.setText(getIntent().getStringExtra("Tipo"));

        horario_id = (TextView)findViewById(R.id.id_horario);
        horario_id.setText(getIntent().getStringExtra("id_horario"));

        horario_id = (TextView)findViewById(R.id.id_horario);
        horario_id.setText(getIntent().getStringExtra("id_horario"));


        id_pet = (TextView)findViewById(R.id.id_pet);
        id_pet.setText(getIntent().getStringExtra("id_pet"));


        id_estabelecimeto = (TextView)findViewById(R.id.id_estabelecimento);
        id_estabelecimeto.setText(getIntent().getStringExtra("id_estabelecimeto"));





    }





    public void registrar_usuario(View v){

        progressDialog = ProgressDialog.show(ConfirmarAgendamentoActivity.this, "Registrando agendamento", "Por favor aguarde", false, false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+servidor+"/woof/registrar_agendamento.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Response_try","response:"+response);
                Log.i("ID do pet","ID:"+id_pet.getText().toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");


                    if(success.equals("1")) {

                        pet.setText("");
                        nome_estabelecimento.setText("");
                        horario.setText("");
                        endereco.setText("");
                        observacao.setText("");
                        dia.setText("");
                        tipo.setText("");
                        Toast.makeText(getApplicationContext(), "Agendamento realizado com sucesso", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //iniciando a activity de perfil
                        finish();
                    }

                    else{
                        Toast.makeText(getApplicationContext(),"Erro no agendamento", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                catch (JSONException e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("pet",pet.getText().toString());
                params.put("nome_estabelecimento",nome_estabelecimento.getText().toString());
                params.put("horario",horario.getText().toString());
                params.put("endereco",endereco.getText().toString());
                params.put("observacao",observacao.getText().toString());
                params.put("dia",dia.getText().toString());
                params.put("tipo",tipo.getText().toString());
                params.put("id_horario",horario_id.getText().toString());
                params.put("estabelecimento_id",String.valueOf(estabelecimentoId));
                params.put("usuario_id",String.valueOf(usuario_id));
                params.put("id_pet",id_pet.getText().toString());
                params.put("servico",servico);
                return params;

            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }



    public void alterar_status(View v){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.17/woof/status_horarios.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");


                    if(success.equals("1")) {

                        //id_wtf.setText("");
                        Toast.makeText(getApplicationContext(), "Status alterado", Toast.LENGTH_SHORT).show();

                    }

                    else{
                        Toast.makeText(getApplicationContext(),"Erro no registro", Toast.LENGTH_SHORT).show();
                    }
                }

                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("id",id_wtf.getText().toString());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


}
