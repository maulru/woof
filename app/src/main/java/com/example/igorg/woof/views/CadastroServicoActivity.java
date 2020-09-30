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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class CadastroServicoActivity extends AppCompatActivity {

    private String server,URL_SERVER;
    private EditText nomeEdit,tipoEdit,obsEdit,tempoEdit,descEdit,valorEdit;
    private Button insereButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);

        server = Servidor.getIp();

        nomeEdit = (EditText) findViewById(R.id.edit_servico_nome);
        descEdit = (EditText) findViewById(R.id.edit_desc_servico);
        tipoEdit = (EditText) findViewById(R.id.edit_tipo_servico);
        tempoEdit = (EditText) findViewById(R.id.edit_tempo_servico);
        valorEdit = (EditText) findViewById(R.id.edit_valor_servico);
        obsEdit = (EditText) findViewById(R.id.edit_observacao_servico);

        insereButt = (Button) findViewById(R.id.btt_cadastrar_servico);

        insereButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insere();
            }
        });



    }

    private void insere() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_servico.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        startActivity(new Intent(getApplicationContext(), VisualizarServicosActivity.class));


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
                params.put("servico_nome",nomeEdit.getText().toString());
                params.put("servico_descricao",descEdit.getText().toString());
                params.put("servico_valor",valorEdit.getText().toString());
                params.put("servico_tempo",tempoEdit.getText().toString());
                params.put("servico_tipo",tipoEdit.getText().toString());
                params.put("servico_observacao",obsEdit.getText().toString());
                params.put("id_estabelecimento", String.valueOf(estabelecimentoId));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }

}
