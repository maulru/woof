package com.example.igorg.woof;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LatLong extends AppCompatActivity {
    TextView latitude, longitude;
    Button addressButton;
    TextView addressTV;
    TextView latLongTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.latlong);


        addressTV = (TextView) findViewById(R.id.addressTV);
        latLongTV = (TextView) findViewById(R.id.latLongTV);

        latitude = (TextView) findViewById(R.id.latLongTV);
        longitude = (TextView) findViewById(R.id.addressTV);


        addressButton = (Button) findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText editText = (EditText) findViewById(R.id.addressET);
                String address = editText.getText().toString();
/*
                GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(address,
                        getApplicationContext(), new GeocoderHandler());
*/
                Handler markers = new Handler();


                markers.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        cadastrar_mapa();

                    }
                }, 3000);


            }
        });





    }

    public class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String latitude;
            String longitude;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    latitude = bundle.getString("lat");
                    longitude = bundle.getString("long");
                    break;
                default:
                    latitude = null;
                    longitude = null;
            }
            latLongTV.setText(latitude);
            addressTV.setText(longitude);


        }


    }


    public void cadastrar_mapa() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.46/peteasy/registrar_markers.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");


                    if (success.equals("1")) {

                        latitude.setText("");
                        longitude.setText("");


                        Toast.makeText(getApplicationContext(), "Latitude e Longitude cadastrados", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro no cadastro", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Latitude", latitude.getText().toString());
                params.put("Longitude", longitude.getText().toString());


                return params;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


}