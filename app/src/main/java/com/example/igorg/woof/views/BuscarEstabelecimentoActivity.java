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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.Recyclers.RecyclerEstabelecimento;
import com.example.igorg.woof.modelo.DataEstabelecimento;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoNome;


public class BuscarEstabelecimentoActivity extends AppCompatActivity {

    List<DataEstabelecimento> DataAdapterClassList;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    JsonArrayRequest jsonArrayRequest ;

    ArrayList<String> Estabelecimento;

    RequestQueue requestQueue ;

    String HTTP_SERVER_URL = "http://192.168.56.1/woof/cardview.php";

    View ChildView ;

    int RecyclerViewClickedItemPOS ;




    EditText pesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_buscar_estabelecimento);

        DataAdapterClassList = new ArrayList<>();

        Estabelecimento = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        pesquisa = (EditText) findViewById(R.id.buscar);



        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        // JSON data web call function call from here.
        JSON_WEB_CALL();

        //RecyclerView Item click listener code starts from here.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(BuscarEstabelecimentoActivity.this, new GestureDetector.SimpleOnGestureListener() {

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


                    wtf();

                    //Printing RecyclerView Clicked item clicked value using Toast Message.
                    Toast.makeText(BuscarEstabelecimentoActivity.this, Estabelecimento.get(RecyclerViewClickedItemPOS), Toast.LENGTH_LONG).show();

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


    public void wtf() {

        Intent intent = new Intent(BuscarEstabelecimentoActivity.this,AgendarBanhoActivity.class);
        //Intent intent = new Intent(ShowAllStudentsActivity.this,VoteActivity.class);


        intent.putExtra("ListViewValue", Estabelecimento.get(RecyclerViewClickedItemPOS));

        startActivity(intent);


        finish();

/*
                Intent intentServicos = new Intent(BuscaEstabelecimento.this,
                        Servicos.class);

                startActivity(intentServicos);

                */


    }




    public void JSON_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataEstabelecimento GetDataAdapter2 = new DataEstabelecimento();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setId(json.getInt("id"));

                GetDataAdapter2.setNome(json.getString("nome"));

                //Adding subject name here to show on click event.
                Estabelecimento.add(json.getString("nome"));

                GetDataAdapter2.setEndereco(json.getString("endereco"));

                GetDataAdapter2.setTelefone(json.getString("telefone"));

            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        progressBar.setVisibility(View.GONE);

        recyclerViewadapter = new RecyclerEstabelecimento(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}
