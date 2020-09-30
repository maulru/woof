package com.example.igorg.woof.views;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.fragments.PageAdapter;
import com.example.igorg.woof.modelo.Servicos;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.igorg.woof.modelo.Servidor.servicoId;

public class VisualizarServicoActivity extends AppCompatActivity {


    TextView ServicoNome,ServicoValor;

    String HTTP_SERVER_URL;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    String server;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_servico);


        ServicoNome = findViewById(R.id.servico_nome);
        ServicoValor = findViewById(R.id.servico_valor);

        server = Servidor.getIp();

        HTTP_SERVER_URL = "http://"+server+"/woof/busca_servico.php?h="+servicoId;

        JSON_WEB_CALL();

    }

    private void JSON_WEB_CALL() {
        Toast.makeText(VisualizarServicoActivity.this,"chamou a função",Toast.LENGTH_SHORT).show();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,HTTP_SERVER_URL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("erro","Erro: "+response);
                        Toast.makeText(VisualizarServicoActivity.this, "Json : " +response.toString(), Toast.LENGTH_SHORT).show();
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(VisualizarServicoActivity.this, "Erro: " +error, Toast.LENGTH_SHORT).show();
                Log.i("erro","Erro: "+error);
            }
        });


        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonObjectRequest);
    }

    private void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject object) {

        int id = 0;
        String nome,valor;

        Servicos GetDataAdapter2 = new Servicos();

                JSONObject json = object;


        try {


            id = json.getInt("ID_Servico");
            nome = json.getString("nome");
            valor = json.getString("valor");

            ServicoNome.setText(nome);
            ServicoValor.setText(valor);




        } catch (JSONException e) {

            e.printStackTrace();
        }


    }
}
