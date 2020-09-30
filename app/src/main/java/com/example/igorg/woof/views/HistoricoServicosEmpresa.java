package com.example.igorg.woof.views;

import android.graphics.Color;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Historico;
import com.example.igorg.woof.modelo.Servidor;

public class HistoricoServicosEmpresa extends AppCompatActivity {

    ListView HistoricoListView;
    ProgressBar progressBar;
    String HttpUrl;
    List<String> IdList = new ArrayList<>();
    String status_cor = "Em andamento";
    String server;
    private TextToSpeech mTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_historico_servicos);

        server = Servidor.getIp();

        HttpUrl = "http://"+server+"/woof/busca_agendamento.php";

        HistoricoListView = (ListView)findViewById(R.id.listview1);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        new HistoricoServicosEmpresa.GetHttpResponse(HistoricoServicosEmpresa.this).execute();


        HistoricoListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(HistoricoServicosEmpresa.this,HistoricoIndividualEmpresaActivity.class);
                //Intent intent = new Intent(ShowAllStudentsActivity.this,VoteActivity.class);


                intent.putExtra("ListViewValue", IdList.get(position).toString());

                startActivity(intent);


                finish();

            }
        });
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

    }


    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<Historico> historicoList;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

            HttpServicesClass httpServicesClass = new HttpServicesClass(HttpUrl);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult = httpServicesClass.getResponse();

                    if(JSonResult != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(JSonResult);

                            JSONObject jsonObject;



                            Historico historico;

                            historicoList = new ArrayList<Historico>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                historico = new Historico();

                                jsonObject = jsonArray.getJSONObject(i);


                                IdList.add(jsonObject.getString("id").toString());

                                historico.status = jsonObject.getString("status").toString();
                                historico.tipo = jsonObject.getString("tipo").toString();
                                historico.pet = jsonObject.getString("pet").toString();
                                historico.data = jsonObject.getString("dia").toString();
                                historico.horario = jsonObject.getString("horario").toString();
                                //historico.status = jsonObject.getString("status").toString();

                                historicoList.add(historico);


                                // = "www";
/*
                                if(historico.status == "Cancelado") {
                                    HistoricoListView.setBackgroundColor(Color.parseColor("#27ceda"));
                                }
*/


                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.GONE);

            HistoricoListView.setVisibility(View.VISIBLE);



            ListAdapterClass adapter = new ListAdapterClass(historicoList, context);

            HistoricoListView.setAdapter(adapter);

        }
    }
    public void falar() {
        mTTS.speak("Historico", TextToSpeech.QUEUE_FLUSH, null);
    }


}
