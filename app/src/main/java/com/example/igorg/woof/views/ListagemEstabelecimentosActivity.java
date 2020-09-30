package com.example.igorg.woof.views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.igorg.peteazyv20.R;

import com.example.igorg.woof.modelo.Estabelecimentos;
import com.example.igorg.woof.modelo.Servidor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;
public class ListagemEstabelecimentosActivity extends AppCompatActivity implements EstabelecimentoAdapter.ContactsAdapterListener {
    private static final String TAG = ListagemEstabelecimentosActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Estabelecimentos> estabelecimentoList;
    private EstabelecimentoAdapter mAdapter;
    private SearchView searchView;
    private TextToSpeech mTTS;
    // url to fetch contacts json
   String URL,server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_estabelecimentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        server = Servidor.getIp();

        URL = "http://"+server+"/woof/cardview.php";

        recyclerView = findViewById(R.id.recycler_view);
        estabelecimentoList = new ArrayList<>();
        mAdapter = new EstabelecimentoAdapter(this, estabelecimentoList, this);




        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        estabelecimentos();
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


    private void estabelecimentos() {
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Não foi possivel exibir os estabelecimentos", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Estabelecimentos> items = new Gson().fromJson(response.toString(), new TypeToken<List<Estabelecimentos>>() {
                        }.getType());

                        estabelecimentoList.clear();
                        estabelecimentoList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Aplicacao.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {


            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onContactSelected(Estabelecimentos contact) {

        if (contact.getTipo().equals("Petshop") ) {
            Intent intent = new Intent(ListagemEstabelecimentosActivity.this, AgendarBanhoActivity.class);
            estabelecimentoId = Integer.valueOf(contact.getId());
            intent.putExtra("nome", contact.getNome().toString());
            intent.putExtra("endereco", contact.getEndereco().toString());
            intent.putExtra("telefone", contact.getTelefone().toString());

            startActivity(intent);

        }

        else if(contact.getTipo() .equals("Clínica Veterinária")){
            Intent intent = new Intent(ListagemEstabelecimentosActivity.this, AgendarConsultaActivity.class);
            estabelecimentoId = Integer.valueOf(contact.getId());
            intent.putExtra("nome", contact.getNome().toString());
            intent.putExtra("endereco", contact.getEndereco().toString());
            intent.putExtra("telefone", contact.getTelefone().toString());

            startActivity(intent);

        }
    }

    public void falar() {
        mTTS.speak("Pesquisar Estabelecimentos", TextToSpeech.QUEUE_FLUSH, null);
    }

}
