package com.example.igorg.woof.views;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingLocation1 {

    private static final String TAG = "GeocodingLocation";

    public static void getAddressFromLocation1(final String locationAddress,
                                              final Context context, final CadastroEstabelecimentoNovo.GeocoderHandler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                String result1 = null;
                try {
                    List
                            addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = (Address) addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        StringBuilder sb1 = new StringBuilder();
                        sb.append(address.getLatitude()).append("\n");
                        sb1.append(address.getLongitude()).append("\n");
                        result = sb.toString();
                        result1 = sb1.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        //result = "\n\nLatitude and Longitude :\n" + result+result1 ;
                        bundle.putString("lat", result);
                        bundle.putString("long", result1);
                        message.setData(bundle);



                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location WTF";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();

                }
            }
        };
        thread.start();

    }






}