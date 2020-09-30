package com.example.igorg.woof.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.Recyclers.RecyclerServico;
import com.example.igorg.woof.modelo.Servicos;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;
import static com.example.igorg.woof.modelo.Servidor.servicoId;
import static com.example.igorg.woof.modelo.Servidor.servicoNome;

public class VisualizarServicosActivity extends AppCompatActivity {

    private RecyclerView servicoList;
    private EditText editText, etd;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;


    String HTTP_SERVER_URL;

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    String server;

    ArrayList<String> idServicos, nomeServicos;

    List<Servicos> DataAdapterClassList;

    int RecyclerViewClickedItemPOS ;

    View ChildView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_servicos2);

        DataAdapterClassList = new ArrayList<>();

        idServicos = new ArrayList<>();
        nomeServicos = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.listServicos);
        recyclerView.setHasFixedSize(true);


        recyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        server = Servidor.getIp();

        HTTP_SERVER_URL = "http://"+server+"/woof/busca_servicos.php?h="+estabelecimentoId;

        JSON_WEB_CALL();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(VisualizarServicosActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked item value.
                    RecyclerViewClickedItemPOS = Recyclerview.getChildAdapterPosition(ChildView);


                    servicoId = Integer.parseInt(idServicos.get(RecyclerViewClickedItemPOS));
                    servicoNome = nomeServicos.get(RecyclerViewClickedItemPOS);

                    visualizar();

                    //Printing RecyclerView Clicked item clicked value using Toast Message.
                    Toast.makeText(VisualizarServicosActivity.this, idServicos.get(RecyclerViewClickedItemPOS), Toast.LENGTH_LONG).show();

                }

                return false;
            }



            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });




    }

    public void JSON_WEB_CALL() {

        Toast.makeText(VisualizarServicosActivity.this, "chamou a função", Toast.LENGTH_SHORT).show();
        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.i("erro", "Erro: " + response);
                        Toast.makeText(VisualizarServicosActivity.this, "Json : " + response.toString(), Toast.LENGTH_SHORT).show();
                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(VisualizarServicosActivity.this, "Erro: " + error, Toast.LENGTH_SHORT).show();
                        Log.i("erro", "Erro: " + error);
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            Servicos GetDataAdapter3 = new Servicos();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter3.setId(json.getInt("ID_Servico"));

                GetDataAdapter3.setNome(json.getString("nome"));

                //Adding subject name here to show on click event.
                idServicos.add(json.getString("ID_Servico"));
                nomeServicos.add(json.getString("nome"));


            } catch (JSONException e) {

                e.printStackTrace();
            }

            DataAdapterClassList.add(GetDataAdapter3);

        }

        recyclerViewadapter = new RecyclerServico(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);

    }

    public void visualizar() {




        Intent intentPet = new Intent(VisualizarServicosActivity.this,
                VisualizarServicoActivity.class);

        startActivity(intentPet);


    }


}
