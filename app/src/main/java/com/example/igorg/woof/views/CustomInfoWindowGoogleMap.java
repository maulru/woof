package com.example.igorg.woof.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.mapa;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    public CustomInfoWindowGoogleMap(Response.Listener<String> listener) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.activity_infoview, null);

        TextView name_tv = view.findViewById(R.id.nome);
        TextView details_tv = view.findViewById(R.id.telefone);


        TextView hotel_tv = view.findViewById(R.id.endereco);


        name_tv.setText(marker.getTitle());
        details_tv.setText(marker.getSnippet());

        mapa map = (mapa) marker.getTag();




        hotel_tv.setText(map.getNome());
        details_tv.setText(map.getEndereco());
        hotel_tv.setText(map.getEndereco());

        return view;
    }
}