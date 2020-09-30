package com.example.igorg.woof.views;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.igorg.peteazyv20.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

import im.delight.android.location.SimpleLocation;


public class Mapppp extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnInfoWindowClickListener {

    MapFragment mapFragment;
    GoogleMap gMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String nome, latitude, longitude,endereco,telefone, titulo,obs;
    Dialog myDialog;
    private TextToSpeech mTTS;

    Context mContext;
    private static final int MY_REQUEST_INT = 10;
    private SimpleLocation locationS;
    Boolean cameraLock;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 300;

    public static final String ID = "id";
    public static final String TITLE = "Nome";
    public static final String TELEFONE = "telefone";
    public static final String ENDERECO = "endereco";
    public static final String LAT = "Latitude";
    public static final String LNG = "Longitude";
    public static final String LAT1 = "latitude";
    public static final String LNG1 = "longitude";
    public static final String observacao = "observacao";
    public static final String titulo1 = "titulo";

    private Context context;

    private String url_mapa = "http://192.168.43.46/peteasy/markers.php";
    private String url = "http://192.168.43.46/peteasy/mapa.php";
    private String url_clinica = "http://192.168.43.46/peteasy/filtro_clinica.php";
    private String url_petshop = "http://192.168.43.46/peteasy/filtro_petshop.php";
    private String url_ong = "http://192.168.43.46/peteasy/filtro_ong.php";
    private String url_animal = "http://192.168.43.46/peteasy/animal.php";

    String tag_json_obj = "json_obj_req";
    Handler handler = new Handler();
    Context C;
    Runnable refresh;

    int ong_filtro = 0;
    int clinica_filtro =0;
    int petshop_filtro =0;
    int animalt = 0;

    private com.github.clans.fab.FloatingActionButton tttt;
    private com.github.clans.fab.FloatingActionButton filtro_clinica;
    private com.github.clans.fab.FloatingActionButton filtro_petshop;
    private com.github.clans.fab.FloatingActionButton filtro_ong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        filtro_clinica = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.ClinicaFiltro);
        filtro_clinica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int animalt=1;
                Toast.makeText(getApplicationContext(),"CLINICA",Toast.LENGTH_LONG).show();
                markerClinica();

            }});


        filtro_ong = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.OngFiltro);

        filtro_ong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int animalt=1;
                Toast.makeText(getApplicationContext(),"ONG",Toast.LENGTH_LONG).show();
                markerONG();

            }});





        filtro_petshop = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.PetshopFiltro);

        filtro_petshop.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Petshop",Toast.LENGTH_LONG).show();

                markerPetshop();

            }});









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


        myDialog = new Dialog(this);

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


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                falar();
            }
        }, 1500);


        getMarkers();
        getAnimal();

    }

    public void falar() {


        mTTS.speak("Tela de Mapa", TextToSpeech.QUEUE_FLUSH, null);
        //mTTS.speak("Igor é GOD", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
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
            gMap.setMyLocationEnabled(true);

        }


        //Aqui é pra setar a camera no usuário ao abrir o APP
        setCameraOpenApp(userPosition);
        setCameraOpenApp(userPosition);







/*
        infoview markerInfoWindowAdapter = new infoview(getApplicationContext());
        googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        googleMap.setOnInfoWindowClickListener(this);
*/






    }

    private void addMarker(LatLng latlng) {
        markerOptions.position(latlng);
        markerOptions.title(nome);
        markerOptions.snippet("Endereço: "+endereco+""+"\nTelefone: " + telefone);

        //markerOptions.snippet(endereco);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.premium_teste1));

        gMap.addMarker(markerOptions);


        if(gMap != null){

            gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.activity_infoview, null);

                    TextView nome = (TextView) v.findViewById(R.id.nome);
                    TextView telefone = (TextView) v.findViewById(R.id.endereco);

                    LatLng ll = marker.getPosition();
                    nome.setText(marker.getTitle());
                    return v;
                }
            });
        }

        gMap.setOnInfoWindowClickListener(this);

        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {


            @Override
            public void onInfoWindowClick(Marker marker) {

                Intent intent = new Intent(Mapppp.this,AgendarConsultaActivity.class);

                startActivity(intent);


            }
        });


    }


    private void addMarker1(LatLng latlng) {
        if(animalt ==0) {
            markerOptions.position(latlng);
            markerOptions.title(nome);
            markerOptions.snippet("Endereço: " + endereco + "" + "\nObsservacao: " + obs);

            //markerOptions.snippet(endereco);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.lostdog));

            // gMap.addMarker(markerOptions);


            if (gMap != null) {

                gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {


                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.activity_infoview, null);

                        TextView nome = (TextView) v.findViewById(R.id.nome);
                        TextView telefone = (TextView) v.findViewById(R.id.endereco);

                        LatLng ll = marker.getPosition();
                        nome.setText(marker.getTitle());
                        return v;
                    }
                });
            }




            gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {


                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        handler.postDelayed(refresh, 1000);

    }


    private void getMarkers() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("mapa");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nome = jsonObject.getString(TITLE);
                        telefone = jsonObject.getString(TELEFONE);
                        endereco = jsonObject.getString(ENDERECO);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
                        latitude = jsonObject.getString(LAT);
                        longitude = jsonObject.getString(LNG);




                        //addMarker(latLng, nome);


                        addMarker(latLng);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(Mapppp.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Aplicacao.getInstance().addToRequestQueue(strReq, tag_json_obj);



    }




    private void markerPetshop () {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_petshop, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("mapa");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nome = jsonObject.getString(TITLE);
                        telefone = jsonObject.getString(TELEFONE);
                        endereco = jsonObject.getString(ENDERECO);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
                        latitude = jsonObject.getString(LAT);
                        longitude = jsonObject.getString(LNG);

                        addMarker(latLng);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(Mapppp.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        Aplicacao.getInstance().addToRequestQueue(strReq, tag_json_obj);
        ;
    }






    private void markerClinica() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_clinica, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("mapa");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nome = jsonObject.getString(TITLE);
                        telefone = jsonObject.getString(TELEFONE);
                        endereco = jsonObject.getString(ENDERECO);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
                        latitude = jsonObject.getString(LAT);
                        longitude = jsonObject.getString(LNG);


                        addMarker(latLng);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(Mapppp.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Aplicacao.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    private void markerONG() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_ong, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("mapa");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nome = jsonObject.getString(TITLE);
                        telefone = jsonObject.getString(TELEFONE);
                        endereco = jsonObject.getString(ENDERECO);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
                        latitude = jsonObject.getString(LAT);
                        longitude = jsonObject.getString(LNG);


                        addMarker(latLng);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(Mapppp.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Aplicacao.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void getAnimal() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_animal, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("mapa");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nome = jsonObject.getString(TITLE);
                        obs = jsonObject.getString(observacao);
                        endereco = jsonObject.getString(ENDERECO);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));
                        latitude = jsonObject.getString(LAT);
                        longitude = jsonObject.getString(LNG);




                        //addMarker(latLng, nome);


                        addMarker1(latLng);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(Mapppp.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Aplicacao.getInstance().addToRequestQueue(strReq, tag_json_obj);



    }





    public void setCameraOpenApp(LatLng userPosition) {
        CameraPosition userCameraPosition =
                new CameraPosition.Builder().target(userPosition)
                        .zoom(14.2f)
                        .bearing(0)
                        .build();
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(userCameraPosition));
    }

    // Esse metodo é somente para quando a localização alterar ele ser chamado no OnLocationChanged e setar a camera
    public void setCamera(LatLng userPosition) {
        CameraPosition userCameraPosition =
                new CameraPosition.Builder().target(userPosition)
                        .zoom(14.2f)
                        .bearing(0)
                        .build();
        gMap.moveCamera(CameraUpdateFactory.newCameraPosition(userCameraPosition));
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }


}

/*
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(Maps.this, Servicos.class);
        startActivity(intent);
    }

    */








