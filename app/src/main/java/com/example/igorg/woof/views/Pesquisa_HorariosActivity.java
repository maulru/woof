package com.example.igorg.woof.views;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Horarios;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class Pesquisa_HorariosActivity extends Activity {

    public static final String HORARIO = "Horario";
    public static final String TIPO = "Tipo";
    public static final String ID  = "Id";
    private RequestQueue mRequestQueue;
    ListView horarioList;
    Horarios_ExcluirAdapter horarios_excluirAdapter;
    ArrayList<Horarios> horariosArray = new ArrayList<Horarios>();
    EditText dia;
    String Tipo,Horario,Id,DIAHOLDER;
    String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa__horarios);

        dia = (EditText)findViewById(R.id.dia);

        DIAHOLDER = getIntent().getStringExtra("dia");

        dia.setText(DIAHOLDER);

        server = Servidor.getIp();

      //  teste();
        teste_dia();

        //exibir(Id,Tipo,Horario);


        horarios_excluirAdapter = new Horarios_ExcluirAdapter(Pesquisa_HorariosActivity.this, R.layout.horarios_row,
                horariosArray);
        horarioList = (ListView) findViewById(R.id.listView);
        horarioList.setItemsCanFocus(false);
        horarioList.setAdapter(horarios_excluirAdapter);

        horarioList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    final int position, long id) {
                Log.i("List View Clicked", "**********");
                Toast.makeText(Pesquisa_HorariosActivity.this, "List View Clicked:" + position, Toast.LENGTH_LONG).show();
            }
        });

    }




    public void exibir(String Id, String Tipo ,String Horario){

        horariosArray.add(new Horarios(Id,Tipo,Horario));

      /*  horariosArray.add(new Horarios("1", "Banho", "10:00"));
        horariosArray.add(new Horarios("2", "Tosa", "11:30"));
        horariosArray.add(new Horarios("3", "Banho", "16:00"));
      */

    }
/*
    public void teste(){

        Id="5";
        Tipo="kjaldj";
        Horario="6565";
        exibir(Id, Tipo, Horario);

    }

*/
    public void teste_dia(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/pesquisa_horario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response: ", response.toString());
                        try {
                         //   JSONObject jObj = new JSONObject(response);
                        //    String getObject = jObj.getString("x");
                           // JSONArray jsonArray = new JSONArray(getObject);
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Id = jsonObject.getString(ID);
                                Tipo = jsonObject.getString(TIPO);
                                Horario= jsonObject.getString(HORARIO);

                               // exibir(Id,Tipo,Horario);

                                Log.i("Array:","Posição:"+i+"Id:"+Id+"Tipo:"+Tipo+"Horario:"+Horario);

                                horariosArray.add(new Horarios(Id,Tipo,Horario));
                                Toast.makeText(Pesquisa_HorariosActivity.this, ""+Horario, Toast.LENGTH_SHORT).show();
                            }
                            horarioList.setAdapter(horarios_excluirAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(getApplicationContext(), "erro de resposta 2: " + error,Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Dia",dia.getText().toString());
                params.put("id_estabelecimento",String.valueOf(estabelecimentoId));
                return params;
            }




        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }




}
