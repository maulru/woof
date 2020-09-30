package com.example.igorg.woof.views;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.InfoWindowData;
import com.example.igorg.woof.modelo.Servidor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import im.delight.android.location.SimpleLocation;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,LocationListener {
    int animalt = 0;
    GoogleMap mMap;
    String tag_json_obj = "json_obj_req";
    private static final int MY_REQUEST_INT = 10;
    private SimpleLocation locationS;
    Boolean cameraLock;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 300;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String nome, latitude, longitude, endereco, telefone, titulo, obs, imagem;
    String hotdog;
    public static final String ID = "ID_Estabelecimento";
    public static final String TITLE = "Nome";
    public static final String TELEFONE = "Telefone";
    public static final String ENDERECO = "Endereco";
    public static String LAT = "Latitude";
    public static String LNG = "Longitude";
    public static final String LAT1 = "latitude";
    public static final String LNG1 = "longitude";
    public static final String observacao = "observacao";
    public static final String titulo1 = "titulo";
    public static final String imagem_marker = "URL_foto";
    public static final String imagem_animal = "url_imagem";
    String LATITUDE;
    String LONGITUDE;
    final InfoWindowData info = new InfoWindowData();
    private Context context;
    Handler handler = new Handler();
    Context C;
    Runnable refresh;

    private String url, server;

    List<String> IdList = new ArrayList<>();
    String teste = "teste";
    private TextToSpeech mTTS;
    ImageView icon;
    int status=0;
    TextView tipo;
    String teste1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        server = Servidor.getIp();
         url = "http://"+server+"/woof/mapa.php";

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationS = new SimpleLocation(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {

        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, (LocationListener) this);


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
        getMarkers();


    }
    public void falar() {


        mTTS.speak("Tela de Mapa", TextToSpeech.QUEUE_FLUSH, null);
        //mTTS.speak("Igor é GOD", TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final double latitude = locationS.getLatitude();
        final double longitude = locationS.getLongitude();
        final LatLng userPosition = new LatLng(latitude, longitude);


        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        locationButton.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 50, 180, 0);


        //getMarkers();

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT);
            }
        } else {
            mMap.setMyLocationEnabled(true);

        }


        //Aqui é pra setar a camera no usuário ao abrir o APP
        setCameraOpenApp(userPosition);
        setCameraOpenApp(userPosition);





    }


    public void setCameraOpenApp(LatLng userPosition) {
        CameraPosition userCameraPosition =
                new CameraPosition.Builder().target(userPosition)
                        .zoom(14.2f)
                        .bearing(0)
                        .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(userCameraPosition));
    }

    // Esse metodo é somente para quando a localização alterar ele ser chamado no OnLocationChanged e setar a camera
    public void setCamera(LatLng userPosition) {
        CameraPosition userCameraPosition =
                new CameraPosition.Builder().target(userPosition)
                        .zoom(14.2f)
                        .bearing(0)
                        .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(userCameraPosition));
    }





    private void getMarkers() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.e("Response: ", response.toString());


                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("estabelecimento");
                    JSONArray jsonArray = new JSONArray(getObject);





                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        info.setId(jsonObject.getString(ID));
                        info.setImage(jsonObject.getString(TITLE));
                        info.setFood( jsonObject.getString(TELEFONE));
                        info.setTransport(jsonObject.getString(ENDERECO));
                        imagem = jsonObject.getString(imagem_marker);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
                        latitude = jsonObject.getString(LAT);
                        longitude = jsonObject.getString(LNG);

                        status=0;
                        addMarker(latLng);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", "Erro:"+error.getMessage());
                Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Aplicacao.getInstance().addToRequestQueue(strReq, tag_json_obj);


    }

    private void addMarker(LatLng latlng) {

        markerOptions.position(latlng);
        String tipo =".";

        markerOptions.title(info.getImage()+tipo+"\nEndereço: "+info.getTransport()+""+"\nTelefone: " +info.getFood() );
        //markerOptions.title(info.getImage());

        // markerOptions.title(info.getImage());



        markerOptions.snippet(imagem);
        //tipo.setVisibility(View.GONE);
        //markerOptions.snippet(endereco);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.premium_teste1));

        mMap.addMarker(markerOptions);


        if (mMap != null) {

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(final Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.activity_infoview, null);
                    //currentMarker = marker;
                    TextView nome = (TextView) v.findViewById(R.id.nome);
                    TextView telefone = (TextView) v.findViewById(R.id.endereco);
                    //ImageView img = (ImageView) v.findViewById(R.id.wtf);


                    icon = (ImageView) v.findViewById(R.id.wtf);


                    String img_url = imagem;
                    // imagem ="http://192.168.43.46/peteasy/uploads/pe1.png";
                    Picasso.get().load(marker.getSnippet())
                            .resize(1000, 1000)
                            .onlyScaleDown().centerCrop().into(icon, new MarkerCallback(marker, marker.getSnippet(), icon));


                    LatLng ll = marker.getPosition();
                    nome.setText(marker.getTitle());
                    return v;
                }
            });
        }
        mMap.setOnInfoWindowClickListener(this);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {


            @Override
            public void onInfoWindowClick(Marker marker) {

                // String[] separated = marker.getTitle().split(".");



                String[] arrayString = marker.getTitle().split("\\.");

                String nome = arrayString[0];

                Intent intent = new Intent(MapsActivity.this, AgendarConsulta_Mapa.class);
                intent.putExtra("nome", nome);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void onBackPressed(){
        Intent intent = new Intent (MapsActivity.this,HomeUserActivity.class);
        startActivity(intent);
    }
}