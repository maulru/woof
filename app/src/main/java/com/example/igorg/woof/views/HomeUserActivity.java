package com.example.igorg.woof.views;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;


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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;import com.squareup.picasso.Picasso;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoEndereco;


public class HomeUserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView foto;
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
    GoogleSignInClient mGoogleSignInClient;
    private static final int MY_REQUEST_INT = 10;

    Usuario usuario;
    private Context context;
    String server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }
        }

        server = Servidor.getIp();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDialog = new Dialog(this);
        img= (ImageView)findViewById(R.id.imagem) ;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        usuario= SharedPrefManager.getInstance(this).getUser();
        usuario.getId();
    //  usuario.getURL_foto();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.i("dados", "ID="+usuario.getId()+" media>"+usuario.getMedia());
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


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        t = (TextView) findViewById(R.id.calendario);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChat = new Intent(HomeUserActivity.this,
                        CalendarioActivity.class);
                startActivity(intentChat);
            }
        });


        Button mapa = (Button) findViewById(R.id.mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMapa = new Intent(HomeUserActivity.this,
                        MapsActivity.class);
                startActivity(intentMapa);
            }
        });



        Button Emergencia = (Button) findViewById(R.id.emergencia);
        Emergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmergencia = new Intent(HomeUserActivity.this,
                        CadastroAnimalAbandonadoActivity.class);
                startActivity(intentEmergencia);
            }
        });

        Button ajude = (Button) findViewById(R.id.ajude);
        ajude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjude = new Intent(HomeUserActivity.this,
                        Ajude.class);
                startActivity(intentAjude);
            }
        });


        rodando = (ImageView)findViewById(R.id.imagem);

        rodando.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(HomeUserActivity.this, R.anim.bounce);
                rodando.startAnimation(myAnim);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(HomeUserActivity.this,ListagemEstabelecimentosActivity.class));
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
        View headerview = navigationView.getHeaderView(0);
        meus_pets =(Button)headerview.findViewById(R.id.amiguinhos);

        meus_pets.setOnClickListener(new View.OnClickListener() {
            @Override    public void onClick(View v) {
                Intent intent = new Intent(HomeUserActivity.this,VisualizarPetsActivity.class);
                startActivity(intent);
            }
        });

        foto=(ImageView) headerview.findViewById(R.id.profile_image);


        String url ="http://"+server+"/woof/uploads/usuario_"+usuario.getId()+".png";

        Log.i("foto_url","foto:"+url);

        Picasso.get().load(url).into(foto);


        nome=(TextView) headerview.findViewById(R.id.nome_user_home);
        String user_name= usuario.getNome();

        nome.setText(user_name);

      //  Log.i("Nome_json", "FOTO:" + usuario.getURL_foto());

        nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeUserActivity.this,VIsualizarPerfilActivity.class);
                startActivity(intent);
            }
        });


        editar =(ImageView) headerview.findViewById(R.id.editar);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override    public void onClick(View v) {
                Intent intent = new Intent(HomeUserActivity.this,VIsualizarPerfilActivity.class);
                startActivity(intent);
            }
        });

        //LOGINS

        /*
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(HomeUserActivity.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personId = acct.getId();

            Usuario user = new Usuario(
                    personId,
                    personName
            );

            //storing the user in shared preferences
            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

        }
*/





    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                Intent i= new Intent(HomeUserActivity.this,HomeUserActivity.class);
                startActivity(i);
                break;
            case R.id.nav_maps:
                Intent g= new Intent(HomeUserActivity.this,MapsActivity.class);
                startActivity(g);
                break;
            case R.id.nav_historico:
                Intent o= new Intent(HomeUserActivity.this,HistoricoServicosActivity.class);
                startActivity(o);
                break;
            case R.id.nav_parceiro:
                Intent r = new Intent(HomeUserActivity.this,CadastroEstabelecimentoNovo.class);
                startActivity(r);
                break;
            case R.id.nav_minhas_empresas:
                Intent empresa = new Intent(HomeUserActivity.this,VisualizarMinhasEmpresasActivity.class);
                startActivity(empresa);
                break;
            case R.id.woof_site:
                Intent site = new Intent(HomeUserActivity.this,QuemSomos.class);
                startActivity(site);
                break;


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            LayoutInflater inflater = (LayoutInflater) HomeUserActivity.this
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(HomeUserActivity.this,R.style.AlertDialog);
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

    protected void onRestart(){
        super.onRestart();
        usuario= SharedPrefManager.getInstance(this).getUser();

        Log.i("dados", "ID="+usuario.getId()+" media>"+usuario.getMedia());
    }

}