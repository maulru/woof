package com.example.igorg.woof.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Estabelecimento;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.voids.SharedPrefManager;
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoEndereco;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoNome;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoTipo;

public class HomeEmpresaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar=null;
    TextView t;
    ImageView rodando;
    Dialog myDialog;
    private PopupWindow mPopupWindow;
    //private Button fechar;
    private TextView fechar;
    Button meus_pets;
    ImageView editar,img;
    private TextView nome;

    String HTTP_SERVER_URL;
    String server;


    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDialog = new Dialog(this);
        img= (ImageView)findViewById(R.id.imagem) ;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        server = Servidor.getIp();

        HTTP_SERVER_URL = "http://"+server+"/woof/busca_estabelecimento.php?h="+estabelecimentoId;

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_empresa);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view_empresa);
        navigationView.setNavigationItemSelectedListener(this);



        Button btnsair = (Button) findViewById(R.id.btnSair);
        btnsair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                //finish();
                System.exit(0);
                */
                sair();

            }
        });


        NavigationView navigationView = findViewById(R.id.nav_view_empresa);
        navigationView.setNavigationItemSelectedListener(this);


        t = (TextView) findViewById(R.id.calendario);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChat = new Intent(HomeEmpresaActivity.this,
                        CalendarioActivity.class);
                startActivity(intentChat);
            }
        });


        View headerview = navigationView.getHeaderView(0);
        nome = (TextView) headerview.findViewById(R.id.text_empresa_nome);
        JSON_WEB_CALL();


        Button Horarios = (Button) findViewById(R.id.gerenciar);
        Horarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmergencia = new Intent(HomeEmpresaActivity.this,
                        GerenciarHorariosDisponiveisActivity.class);
                startActivity(intentEmergencia);
            }
        });

        Button Agendamentos = (Button) findViewById(R.id.agendamentos);
        Agendamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjude = new Intent(HomeEmpresaActivity.this,
                        HistoricoServicosEmpresa.class);
                startActivity(intentAjude);
            }
        });


        rodando = (ImageView)findViewById(R.id.imagem);

        rodando.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(HomeEmpresaActivity.this, R.anim.rotation);
                rodando.startAnimation(myAnim);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(estabelecimentoTipo.equals("Petshop")){
                            startActivity(new Intent(HomeEmpresaActivity.this,AgendarBanhoEmpresa.class));
                        }else{
                            startActivity(new Intent(HomeEmpresaActivity.this,AgendarConsultaEmpresa.class));
                        }
                    }
                }, 1000);

            }
        });





        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM");
        String strDate = mdformat.format(calendar.getTime());
        TextView textView = (TextView) findViewById(R.id.calendario);
        textView.setText(strDate);

/*

        View headView = navigationView.getHeaderView(R.id.header);
        ImageView editar = headView.findViewById(R.id.editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,EditarPerfil.class);
                startActivity(i);
            }
        });

*/
        /*meus_pets =(Button)headerview.findViewById(R.id.amiguinhos);

        meus_pets.setOnClickListener(new View.OnClickListener() {
            @Override    public void onClick(View v) {
                Intent intent = new Intent(HomeEmpresaActivity.this,VisualizarPetsActivity.class);
                startActivity(intent);
            }
        });
*/




    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_empresa);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent i= new Intent(HomeEmpresaActivity.this,HomeEmpresaActivity.class);
                startActivity(i);
                break;
            case R.id.nav_maps:
                Intent g= new Intent(HomeEmpresaActivity.this,MapsActivity.class);
                startActivity(g);
                break;
            case R.id.nav_historico:
                Intent o= new Intent(HomeEmpresaActivity.this,HistoricoServicosActivity.class);
                startActivity(o);
                break;
            case R.id.nav_parceiro:
                Intent r = new Intent(HomeEmpresaActivity.this,HomeUserActivity.class);
                startActivity(r);
                break;

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_empresa);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

/*
    public void displayPopupWindow() {
        PopupWindow popup = new PopupWindow(this);
        View layout = getLayoutInflater().inflate(R.layout.popup_notificacoes, null);
        popup.setContentView(layout);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }
*/

    private void initiatePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) HomeEmpresaActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup));
            mPopupWindow = new PopupWindow(layout, 800, 670, true);
            mPopupWindow.showAtLocation(layout, Gravity.TOP | Gravity.RIGHT, 0, 0);

            fechar = (TextView) layout.findViewById(R.id.fechar);
            fechar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    public void sair(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeEmpresaActivity.this,R.style.AlertDialog);
        builder.setMessage("Deseja sair do Woof");
        builder.setCancelable(true);
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();

            }
        });
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccessToken.setCurrentAccessToken(null);
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                AccessToken.setCurrentAccessToken(null);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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



        requestQueue = Volley.newRequestQueue(HomeEmpresaActivity.this);

        requestQueue.add(jsonObjectRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject object) {

        int id = 0;
        String nome_estab, raca, tipo;


        Estabelecimento GetDataAdapter2 = new Estabelecimento();

        JSONObject json = object;
        try {


            id = json.getInt("ID_Estabelecimento");
            nome_estab = json.getString("Nome");
            estabelecimentoNome = nome_estab;

            estabelecimentoEndereco = json.getString("Endereco");

            estabelecimentoTipo = json.getString("Tipo");

            Log.i("Nome_json", "Nome:" + nome_estab +" endereco:"+estabelecimentoEndereco);

            nome.setText(nome_estab);


        } catch (JSONException e) {

            e.printStackTrace();
        }

    }
}



