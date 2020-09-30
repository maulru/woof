package com.example.igorg.woof.views;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.Recyclers.RecyclerPet;
import com.example.igorg.woof.modelo.Pet;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.igorg.woof.modelo.Servidor.petId;
import static com.example.igorg.woof.modelo.Servidor.petNome;
import static com.example.igorg.woof.modelo.Servidor.usuario_rede;

public class VisualizarPetsActivity extends AppCompatActivity {

    private RecyclerView petList;
    private EditText editText, etd;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    String myUserId;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;


    String HTTP_SERVER_URL;

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    String server;

    ArrayList<String> idPets, nomePets, fotoPets;

    List<Pet> DataAdapterClassList;

    int RecyclerViewClickedItemPOS ;
    Usuario usuario;
    View ChildView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        usuario= SharedPrefManager.getInstance(this).getUser();

        DataAdapterClassList = new ArrayList<>();

        idPets = new ArrayList<>();
        nomePets = new ArrayList<>();
        fotoPets = new ArrayList<>();

        myUserId = usuario.getId();

        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);


        recyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);





        server = Servidor.getIp();

        HTTP_SERVER_URL = "http://"+server+"/woof/cardview_pets.php?h="+usuario.getId()+"&usuario_rede="+usuario.getMedia();

        JSON_WEB_CALL();

        FloatingActionButton fab = findViewById(R.id.pet_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChat = new Intent(VisualizarPetsActivity.this,
                        CadastroPetActivity.class);
                startActivity(intentChat);
            }
        });

        //RecyclerView Item click listener code starts from here.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(VisualizarPetsActivity.this, new GestureDetector.SimpleOnGestureListener() {

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


                    petId = Integer.parseInt(idPets.get(RecyclerViewClickedItemPOS));
                    petNome = nomePets.get(RecyclerViewClickedItemPOS);

                    visualizar();


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

        Log.i("userid","userid:"+usuario.getId()+ " usermedia:"+usuario.getMedia());

          jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,HTTP_SERVER_URL,null,


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.i("sucesso", "Sucesso: " + response);
                          JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                       // Toast.makeText(VisualizarPetsActivity.this, "2Erro: " + error, Toast.LENGTH_LONG).show();

                        Log.i("erro", "Erro2: " + error);
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            Pet GetDataAdapter2 = new Pet();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setId(json.getInt("ID_Animal"));

                GetDataAdapter2.setPet_nome(json.getString("Nome"));

                //Adding subject name here to show on click event.
                idPets.add(json.getString("ID_Animal"));
                nomePets.add(json.getString("Nome"));
                fotoPets.add(json.getString("URL_foto"));


            } catch (JSONException e) {

                e.printStackTrace();
            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerPet(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);

    }

    public void visualizar() {




        Intent intentPet = new Intent(VisualizarPetsActivity.this,
                VisualizarPetActivity.class);

        startActivity(intentPet);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DataAdapterClassList.clear();
            JSON_WEB_CALL();
    }


}

