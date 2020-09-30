package com.example.igorg.woof.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.igorg.woof.masks.Mask;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.petId;

public class CadastroVacinaActivity extends AppCompatActivity {

    private String server;
    private EditText nome_vacina,data_vacina,quantidade_vacina;
    private Button insere_vacina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_vacina);

        server = (String) Servidor.getIp();

        nome_vacina = (EditText) findViewById(R.id.edit_vacina_tipo);
        data_vacina = (EditText) findViewById(R.id.edit_vacina_data);
        quantidade_vacina = (EditText) findViewById(R.id.edit_vacina_quantidade);

        insere_vacina = (Button) findViewById(R.id.btt_vacina_inserir);

        //máscara de InputView
        final EditText campo_data_nascimento = (EditText) findViewById(R.id.edit_vacina_data);
        campo_data_nascimento.addTextChangedListener(Mask.insert("##/##/####", campo_data_nascimento));

        insere_vacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insere();

            }
        });




    }

    private void insere() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_vacina.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //convertendo a resposta em um objeto json
                            JSONObject obj = new JSONObject(response);

                            //se não houver nenhum erro de resposta

                            Toast.makeText(getApplicationContext(), "Sem erro de reposta", Toast.LENGTH_SHORT).show();

                            //iniciando a activity de perfil
                            finish();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro de resposta 2", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Tipo",nome_vacina.getText().toString());
                params.put("Data_Vacinacao",data_vacina.getText().toString());
                params.put("Quantidade",quantidade_vacina.getText().toString());
                params.put("ID_Animal", String.valueOf(petId));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }
}
