package com.example.igorg.woof.views;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
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
import com.example.igorg.woof.Recyclers.RecyclerMeusEstabelecimentos;
import com.example.igorg.woof.modelo.Estabelecimento;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoNome;

public class VisualizarMinhasEmpresasActivity extends AppCompatActivity {

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

    ArrayList<String> idEstabelecimentos, nomeEstabelecimentos, fotoEstabelecimentos;

    List<Estabelecimento> DataAdapterClassList;

    private TextToSpeech mTTS;

    int RecyclerViewClickedItemPOS ;
    Usuario usuario;
    View ChildView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_minhas_empresas);

        usuario= SharedPrefManager.getInstance(this).getUser();

        DataAdapterClassList = new ArrayList<>();

        idEstabelecimentos = new ArrayList<>();
        nomeEstabelecimentos = new ArrayList<>();
        fotoEstabelecimentos = new ArrayList<>();

        myUserId = usuario.getId();

        recyclerView = (RecyclerView) findViewById(R.id.empresa_list);
        recyclerView.setHasFixedSize(true);


        recyclerViewlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        server = Servidor.getIp();

        HTTP_SERVER_URL = "http://"+server+"/woof/cardview_empresa_user.php?h="+usuario.getId()+"&usuario_rede="+usuario.getMedia();

        JSON_WEB_CALL();


        //RecyclerView Item click listener code starts from here.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(VisualizarMinhasEmpresasActivity.this, new GestureDetector.SimpleOnGestureListener() {

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


                    estabelecimentoId = Integer.parseInt(idEstabelecimentos.get(RecyclerViewClickedItemPOS));
                    estabelecimentoNome = nomeEstabelecimentos.get(RecyclerViewClickedItemPOS);

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
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locale = new Locale("pt");
                    int result = mTTS.setLanguage(locale);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                falar();
            }
        }, 1500);

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

                        Toast.makeText(VisualizarMinhasEmpresasActivity.this, "2Erro: " + error, Toast.LENGTH_LONG).show();

                        Log.i("erro", "Erro2: " + error);
                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }


    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {

            Estabelecimento GetDataAdapter2 = new Estabelecimento();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setEstabelecimento_id(json.getString("ID_Estabelecimento"));

                GetDataAdapter2.setEstabelecimento_nome(json.getString("Nome"));

                //Adding subject name here to show on click event.
                idEstabelecimentos.add(json.getString("ID_Estabelecimento"));
                nomeEstabelecimentos.add(json.getString("Nome"));


            } catch (JSONException e) {

                e.printStackTrace();
            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        recyclerViewadapter = new RecyclerMeusEstabelecimentos(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);

    }

    public void visualizar() {




        Intent intentPet = new Intent(VisualizarMinhasEmpresasActivity.this,
                HomeEmpresaActivity.class);

        startActivity(intentPet);


    }
    public void falar() {
        mTTS.speak("Meus Estabelecimentos cadastrados", TextToSpeech.QUEUE_FLUSH, null);
    }


}
