package com.example.igorg.woof.views;

import android.widget.ImageView;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MarkerCallback implements Callback {
    Marker marker=null;
    String URL;
    ImageView userPhoto;


    MarkerCallback(Marker marker, String URL, ImageView userPhoto) {
        this.marker=marker;
        this.URL = URL;
        this.userPhoto = userPhoto;
    }


    @Override
    public void onSuccess() {
        if (marker != null && marker.isInfoWindowShown()) {
            marker.hideInfoWindow();

            Picasso.get()
                    .load(URL)
                    .into(userPhoto);

            marker.showInfoWindow();
        }
    }

    @Override
    public void onError(Exception e) {

    }
}