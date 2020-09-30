package com.example.igorg.woof.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.Recyclers.RecyclerVacina;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Vacina;
import com.example.igorg.woof.views.VisualizarPetActivity;
import com.example.igorg.woof.views.VisualizarServicosActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.igorg.woof.modelo.Servidor.petId;
import static com.example.igorg.woof.modelo.Servidor.servicoId;
import static com.example.igorg.woof.modelo.Servidor.servicoNome;
import static com.example.igorg.woof.modelo.Servidor.vacinaId;

public class VacinaFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;

    String server;

    JsonArrayRequest jsonArrayRequest;
    Context context;

    int RecyclerViewClickedItemPOS;

    String HTTP_SERVER_URL,VACINAS_SERVER_URL;

    RequestQueue requestQueue;

    ArrayList<String> idVacinas, nomeVacinas;

    List<Vacina> DataAdapterClassList;

    JsonObjectRequest jsonObjectRequest;

    RecyclerView.Adapter recyclerViewadapter;

    View ChildView ;


    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        server = Servidor.getIp();

        DataAdapterClassList = new ArrayList<>();


        View rootView = inflater.inflate(R.layout.fragment_vacina, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.RecyclerVacinas);

        recyclerViewlayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);


        json();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {


            GestureDetector gestureDetector = new GestureDetector(getContext().getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                ChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked item value.
                   // RecyclerViewClickedItemPOS = recyclerView.getChildAdapterPosition(ChildView);


                 //   vacinaId = Integer.parseInt(idVacinas.get(RecyclerViewClickedItemPOS));
                //    servicoNome = nomeVacinas.get(RecyclerViewClickedItemPOS);

               //     Log.i("Vacina","A vacina "+ vacinaId + "foi selecionada");


                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });




        VisualizarPetActivity fragment = new VisualizarPetActivity();
        fragment.getSupportFragmentManager();


        return rootView;



    }

    public void json(){
        VACINAS_SERVER_URL = "http://"+server+"/woof/busca_vacina.php?h="+petId;

        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,VACINAS_SERVER_URL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.i("Sucesso","Sucesso_Response: "+response);
                        json2(response);
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ErroResponse","ErroVolley: "+error);
            }
        });


        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());


        requestQueue.add(jsonArrayRequest);

    }


    public void json2(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            Vacina GetDataAdapter2 = new Vacina();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setID_Vacinas(json.getInt("ID_Vacinas"));

                GetDataAdapter2.setTipo(json.getString("Tipo"));

                GetDataAdapter2.setData_Vacinacao(json.getString("Data_Vacinacao"));

                GetDataAdapter2.setQuantidade(json.getString("Quantidade"));

                Log.i("Json2","Try com sucesso");

            } catch (JSONException e) {

                e.printStackTrace();

                Log.i("Json2", "Json com erro:"+e);
            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerVacina(DataAdapterClassList, getActivity().getApplicationContext());


        recyclerView.setAdapter(recyclerViewadapter);

    }
}
