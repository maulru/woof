package com.example.igorg.woof.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Pet;

import java.util.ArrayList;
import java.util.List;

public class VisualizarAtendimentosActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String myUserId;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;


    String HTTP_SERVER_URL;

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    String server;

    ArrayList<String> idPets, nomePets;

//    List<Atendimentos> DataAdapterClassList;

    int RecyclerViewClickedItemPOS ;

    View ChildView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_atendimentos);
    }
}
