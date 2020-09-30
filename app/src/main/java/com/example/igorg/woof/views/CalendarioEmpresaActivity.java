package com.example.igorg.woof.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.desai.vatsal.mydynamiccalendar.EventModel;
import com.desai.vatsal.mydynamiccalendar.GetEventListListener;
import com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar;
import com.desai.vatsal.mydynamiccalendar.OnDateClickListener;
import com.desai.vatsal.mydynamiccalendar.OnEventClickListener;
import com.desai.vatsal.mydynamiccalendar.OnWeekDayViewClickListener;
import com.example.igorg.peteazyv20.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class CalendarioEmpresaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MyDynamicCalendar myCalendar;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myCalendar = (MyDynamicCalendar) findViewById(R.id.myCalendar);

        setSupportActionBar(toolbar);


        myCalendar.showMonthViewWithBelowEvents();

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("date", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("date", String.valueOf(date));
            }
        });


        myCalendar.addEvent("5-10-2019", "23:00", "Banho Jorginho");
        myCalendar.addEvent("05-10-2019", "8:15", "Tosa Mariazinha");
        myCalendar.addEvent("05-10-2019", "8:30", "Consulta Jorginho");
        myCalendar.addEvent("05-10-2019", "8:45", "Consulta Chuck Norris");
        myCalendar.addEvent("8-10-2019", "8:00", "Ronaldo Brilha muito no Corinthians");
        myCalendar.addEvent("08-10-2019", "9:00", "Palmeiras não tem mundial");

        myCalendar.getEventList(new GetEventListListener() {
            @Override
            public void eventList(ArrayList<EventModel> eventList) {

                Log.e("tag", "eventList.size():-" + eventList.size());
                for (int i = 0; i < eventList.size(); i++) {
                    Log.e("tag", "eventList.getStrName:-" + eventList.get(i).getStrName());
                }

            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.diario:
                empresa();
                return true;
            case R.id.mes:
                usuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




    private void usuario() {

        myCalendar.showMonthViewWithBelowEvents();

        myCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onClick(Date date) {
                Log.e("dia", String.valueOf(date));
            }

            @Override
            public void onLongClick(Date date) {
                Log.e("dia", String.valueOf(date));
            }
        });

    }



    private void empresa() {

        myCalendar.showDayView();

        myCalendar.setOnEventClickListener(new OnEventClickListener() {
            @Override
            public void onClick() {
                Log.e("empresa_calendario", "Calendario Usuario");

            }

            @Override
            public void onLongClick() {
                Log.e("empresa_calendario", "Calendario Usuario");

            }
        });

        myCalendar.setOnWeekDayViewClickListener(new OnWeekDayViewClickListener() {
            @Override
            public void onClick(String date, String time) {
                Log.e("mostra dia", "");
                Log.e("tag", "dia:-" + date + " hora:-" + time);
            }

            @Override
            public void onLongClick(String date, String time) {
                Log.e("mostra dia", "");
                Log.e("tag", "dia:-" + date + " hora:-" + time);
            }
        });

    }


    private void parseJSON() {
        String url = "http://192.168.43.46/peteasy/calendario.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("calendario");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String dia = hit.getString("data");
                                String horario = hit.getString("horario");
                                String nome = hit.getString("nome");

                                myCalendar.addEvent(dia, horario, nome);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


}