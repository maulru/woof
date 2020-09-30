package com.example.igorg.woof.views;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
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


public class UsuarioInfo  extends AppCompatActivity {
    TextView data,tipo, horario,id_horario,pet;


    String horario_spinner, pet_spinner;
    String IDholder, EnderecoHolder;
    TextView ID;

    String server;
    public static final String TITLE = "nome";
    public static final String EMAIL = "email";
    TextView nome_txt,email_txt,tel;
    String nome,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_info);


        server = Servidor.getIp();
        nome_txt= (TextView) findViewById(R.id.id_nome);
        email_txt= (TextView) findViewById(R.id.id_email);
        IDholder = getIntent().getStringExtra("id");
        ID = (TextView) findViewById(R.id.id);
        ID.setText(IDholder);


        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));



        usuario_info();

    }



    public void usuario_info(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/usuario_info.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response: ", response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String getObject = jObj.getString("mapa");
                            JSONArray jsonArray = new JSONArray(getObject);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                nome = jsonObject.getString(TITLE);
                                email = jsonObject.getString(EMAIL);

                                nome_txt.setText(nome);
                                email_txt.setText(email);



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
                params.put("id",ID.getText().toString());

                return params;
            }




        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }




}
