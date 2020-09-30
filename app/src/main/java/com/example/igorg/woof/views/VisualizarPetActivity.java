package com.example.igorg.woof.views;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.example.igorg.woof.Recyclers.RecyclerEstabelecimento;
import com.example.igorg.woof.Recyclers.RecyclerVacina;
import com.example.igorg.woof.fragments.AgendaFragment;
import com.example.igorg.woof.fragments.PageAdapter;
import com.example.igorg.woof.fragments.VacinaFragment;
import com.example.igorg.woof.modelo.DataEstabelecimento;
import com.example.igorg.woof.modelo.Pet;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Vacina;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.igorg.woof.modelo.Servidor.petId;

public class VisualizarPetActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabagenda;
    TabItem tabvacina;

    RecyclerView recyclerView;

    TextView PetNome,PetRaca,PetTipo;

    List<Vacina> DataAdapterClassList;

    String HTTP_SERVER_URL,VACINAS_SERVER_URL;
    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    String server;
    JsonObjectRequest jsonObjectRequest;

    RecyclerView.Adapter recyclerViewadapter;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_pet);




        mContext = getApplicationContext();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);
        tabagenda = findViewById(R.id.tabAgenda);
        tabvacina = findViewById(R.id.tabVacinas);
        PetNome = findViewById(R.id.pet_nome);
        PetTipo = findViewById(R.id.pet_tipo);
        PetRaca = findViewById(R.id.pet_raca);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        server = Servidor.getIp();

        HTTP_SERVER_URL = "http://"+server+"/woof/busca_pet.php?h="+petId;

        VACINAS_SERVER_URL = "http://"+server+"/woof/busca_vacina.php?h="+petId;;

        JSON_WEB_CALL();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {

                    new AgendaFragment();

                    recyclerView = (RecyclerView) findViewById(R.id.RecyclerAgenda);

                } else if (tab.getPosition() == 1) {
                    new VacinaFragment();

                    recyclerView = (RecyclerView) findViewById(R.id.RecyclerVacinas);

                    FloatingActionButton fab = findViewById(R.id.petvacina_fab);

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentChat = new Intent(VisualizarPetActivity.this,
                                    CadastroVacinaActivity.class);
                            startActivity(intentChat);
                        }
                    });










                } else {

                }
            }




            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }




    public void JSON_WEB_CALL(){

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,HTTP_SERVER_URL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Sucesso","JSONObject: "+response);

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ResponseError","Erro: "+error);
            }
        });



        requestQueue = Volley.newRequestQueue(VisualizarPetActivity.this);

        requestQueue.add(jsonObjectRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject object) {

        int id = 0;
        String nome,raca,tipo;



            Pet GetDataAdapter2 = new Pet();

            JSONObject json = object;
            try {


                id = json.getInt("ID_Animal");
                nome = json.getString("Nome");
                tipo = json.getString("Tipo");
                raca = json.getString("Raca");


                PetRaca.setText(raca);
                PetNome.setText(nome);
                PetTipo.setText(tipo);




            } catch (JSONException e) {

                e.printStackTrace();
            }






    }

   public void json(Context context) {


        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,VACINAS_SERVER_URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.i("Sucesso","Erro: "+response);
                        json2(response);
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Erro resposta","Erro: "+error);
            }
        });


        requestQueue = Volley.newRequestQueue(context);


        requestQueue.add(jsonObjectRequest);
    }

    public void json2(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            Vacina GetDataAdapter2 = new Vacina();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setID_Vacinas(json.getInt("ID_Vacinas"));

                GetDataAdapter2.setTipo(json.getString("Tipo"));

                GetDataAdapter2.setTipo(json.getString("Tipo"));
                GetDataAdapter2.setQuantidade(json.getString("Quantidade"));
                GetDataAdapter2.setTipo(json.getString("Tipo"));

            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerVacina(DataAdapterClassList, getApplicationContext());


        recyclerView.setAdapter(recyclerViewadapter);





    }


    @Override
    protected void onResume() {
        super.onResume();



        // The activity has become visible (it is now "resumed").

        JSON_WEB_CALL();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {

                    new AgendaFragment();

                } else if (tab.getPosition() == 1) {
                    new VacinaFragment();

                    recyclerView = (RecyclerView) findViewById(R.id.RecyclerVacinas);

                    FloatingActionButton fab = findViewById(R.id.petvacina_fab);

                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentChat = new Intent(VisualizarPetActivity.this,
                                    CadastroVacinaActivity.class);
                            startActivity(intentChat);
                        }
                    });










                } else {

                }
            }




            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }


}
