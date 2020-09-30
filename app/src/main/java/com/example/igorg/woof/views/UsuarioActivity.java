package com.example.igorg.woof.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UsuarioActivity extends AppCompatActivity {
    String NomeHolder,server;
    TextView NOME;
    public static final String TITLE = "Nome";
    public static final String DT_NASCIMENTO = "Dt_Nascimento";
    public static final String EMAIL = "Email";
    public static final String TELEFONE = "Telefone";
    public static final String ENDERECO = "Endereco";
    public static final String ID = "ID_Uusario";
    String  nome, dt_nasc,email,telefone,endereco,id;
    EditText nome_txt;
    TextView  dt_nasc_txt,email_txt,telefone_txt,endereco_txt,id_txt;

   @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

       nome_txt= (EditText) findViewById(R.id.nome_txt);
       //ENDERECO = (TextView)findViewById(R.id.Endereco);

       NomeHolder = getIntent().getStringExtra("nome");

       nome_txt.setText(NomeHolder);

       server = Servidor.getIp();

       id_txt= (TextView) findViewById(R.id.id_txt);

       endereco_txt= (TextView) findViewById(R.id.endereco_txt);
       telefone_txt= (TextView) findViewById(R.id.telefone_txt);
       email_txt =(TextView) findViewById(R.id.email_txt);
       dt_nasc_txt= (TextView) findViewById(R.id.dt_nasc_txt);

       info_usuario();




   }



    public void info_usuario(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/usuario_info.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response: ", response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String getObject = jObj.getString("usuario");
                            JSONArray jsonArray = new JSONArray(getObject);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                nome = jsonObject.getString(TITLE);
                                dt_nasc = jsonObject.getString(DT_NASCIMENTO);
                                email = jsonObject.getString(EMAIL);
                                telefone = jsonObject.getString(TELEFONE);
                                endereco = jsonObject.getString(ENDERECO);
                                id= jsonObject.getString(ID);


                                nome_txt.setText(nome);
                                dt_nasc_txt.setText(dt_nasc);
                                email_txt.setText(email);
                                telefone_txt.setText(telefone);
                                endereco_txt.setText(endereco);
                                id_txt.setText(id);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro de resposta 2: " + error,Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",nome_txt.getText().toString());

                return params;
            }




        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }



}
