package com.example.igorg.woof.views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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


import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuarios;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ListagemUsuarioActivity extends AppCompatActivity implements UsuarioAdapter.ContactsAdapterListener {
    private static final String TAG = ListagemUsuarioActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Usuarios> usuariosList;
    private UsuarioAdapter mAdapter;
    private SearchView searchView;

    // url to fetch contacts json
    String URL ,servidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_usuario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title_usuario);

        recyclerView = findViewById(R.id.recycler_view_usuario);
        usuariosList = new ArrayList<>();
        mAdapter = new UsuarioAdapter(this, usuariosList, this);

        servidor = Servidor.getIp();

        URL = "http://"+servidor+"/woof/usuarios.php";


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        usuarios();
    }


    private void usuarios() {
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Não foi possivel exibir os usuarios", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.e("Response","Response_user:"+response);
                        List<Usuarios> items = new Gson().fromJson(response.toString(), new TypeToken<List<Usuarios>>() {
                        }.getType());

                        usuariosList.clear();
                        usuariosList.addAll(items);

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

                try {
                    CardView cv_ingredient = (CardView) findViewById(R.id.cardView);
                    cv_ingredient.setVisibility(View.VISIBLE);
                    mAdapter.getFilter().filter(query);

                } catch (Throwable e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(), "Usuario não existe, busque novamente", Toast.LENGTH_SHORT).show();

                }

              //  mAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {

                try {


                    CardView cv_ingredient = (CardView) findViewById(R.id.cardView);

                    cv_ingredient.setVisibility(View.VISIBLE);
                    mAdapter.getFilter().filter(query);

                } catch (Throwable e) {
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(), "Usuario não existe, busque novamente", Toast.LENGTH_SHORT).show();

                }
                return false;
            }});

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
    public void onContactSelected(Usuarios contact) {
        Intent intent = new Intent(ListagemUsuarioActivity.this,UsuarioActivity.class);
        intent.putExtra("nome", contact.getID_Usuario().toString());
        Toast.makeText(getApplicationContext(), "ID: " + contact.getID_Usuario() + ", Telefone: "+", Email: " + contact.getEmail(), Toast.LENGTH_LONG).show();

            startActivity(intent);
           // Toast.makeText(getApplicationContext(), "ID: " + contact.getId() + ", Telefone: "+", Email: " + contact.getEmail(), Toast.LENGTH_LONG).show();
        }
    }

