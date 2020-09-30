package com.example.igorg.woof.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.sax.EndElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Locale;

public class HistoricoIndividualActivity extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;


    String url;
    String server;
    String url_cancelar;

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView TIPO,PET,DIA,HORARIO,LOCAL,ENDERECO,OBSERVACAO,AVALIACAO,STATUS,teste;
    Float f;
    String TIPOHolder, PETHolder, DIAHolder,HORARIOHOLDER,LOCALHOLDER,ENDERECOHOLDER,OBSERVACAOHOLDER,AVALIACAOHOLDER,STATUSHOLDER,DETALHESHOLDER;
    Button UpdateButton, CanelarButton,AvalieButton,DetalhesButton;
    String TempItem;
    ProgressDialog progressDialog2;
    public RatingBar rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_individual);

        TIPO = (TextView)findViewById(R.id.evento);
        PET = (TextView)findViewById(R.id.pet);
        DIA = (TextView)findViewById(R.id.dia);
        HORARIO= (TextView)findViewById(R.id.horario);
        LOCAL= (TextView)findViewById(R.id.nome_estabelecimento);
        ENDERECO= (TextView)findViewById(R.id.endereco);
        OBSERVACAO= (TextView)findViewById(R.id.observacao);
        AVALIACAO= (TextView)findViewById(R.id.avaliacao);
        STATUS= (TextView)findViewById(R.id.status);


        //teste= (TextView)findViewById(R.id.teste);

        //RatingBar rating = (RatingBar) findViewById(R.id.ratingBar);


        //String yourString = "3.5";



        server = Servidor.getIp();
         url = "http://"+server+"/woof/filtro_historico.php";


         url_cancelar = "http://"+server+"/woof/update_status_agendamento.php";


        UpdateButton = (Button)findViewById(R.id.buttonUpdate);
        CanelarButton = (Button)findViewById(R.id.buttonDelete);
        AvalieButton = (Button)findViewById(R.id.avaliar);
        DetalhesButton = (Button)findViewById(R.id.detalhes);


        TempItem = getIntent().getStringExtra("ListViewValue");



        HttpWebCall(TempItem);


        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HistoricoIndividualActivity.this,Update_Historico.class);


                intent.putExtra("Id", TempItem);
                intent.putExtra("Tipo", TIPOHolder);
                intent.putExtra("Pet", PETHolder);
                intent.putExtra("Dia", DIAHolder);
                intent.putExtra("Horario", HORARIOHOLDER);
                intent.putExtra("Local",LOCALHOLDER);
                intent.putExtra("Endereco", ENDERECOHOLDER);
                intent.putExtra("Observacao", OBSERVACAOHOLDER);
                intent.putExtra("Avaliacao", AVALIACAOHOLDER);
                intent.putExtra("Status", STATUSHOLDER);



                startActivity(intent);

                finish();

            }
        });


        CanelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelar_diaologo(view);
                //Cancelar(TempItem);

            }
        });


        AvalieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HistoricoIndividualActivity.this,AvalieActivity.class);


                intent.putExtra("Id", TempItem);

                startActivity(intent);

            }
        });


        DetalhesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HistoricoIndividualActivity.this,DetalhesServicosActivity.class);


                intent.putExtra("Id", TempItem);
                intent.putExtra("detalhes", DETALHESHOLDER);


                startActivity(intent);

                finish();

            }
        });

    }



    public void Cancelar(final String HistoricoID) {

        class CancelarClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(HistoricoIndividualActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(HistoricoIndividualActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            protected String doInBackground(String... params) {


                hashMap.put("id", params[0]);


                finalResult = httpParse.postRequest(hashMap, url_cancelar);

                return finalResult;
            }
        }

        CancelarClass cancelamento = new CancelarClass();

        cancelamento.execute(HistoricoID);
    }


    //Method to show current record Current Selected Record
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(HistoricoIndividualActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(HistoricoIndividualActivity.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("id",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, url);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

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
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);


                            TIPOHolder = jsonObject.getString("tipo").toString() ;
                            PETHolder = jsonObject.getString("pet").toString() ;
                            DIAHolder = jsonObject.getString("dia").toString() ;
                            HORARIOHOLDER= jsonObject.getString("horario").toString() ;
                            LOCALHOLDER= jsonObject.getString("nome_estabelecimento").toString() ;
                            ENDERECOHOLDER= jsonObject.getString("endereco").toString() ;
                            OBSERVACAOHOLDER= jsonObject.getString("observacao").toString() ;
                            AVALIACAOHOLDER= jsonObject.getString("rating").toString() ;
                            STATUSHOLDER= jsonObject.getString("status").toString() ;
                            DETALHESHOLDER= jsonObject.getString("detalhes_atendimento").toString() ;

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
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





            TIPO.setText(TIPOHolder);
            PET.setText(PETHolder);
            DIA.setText(DIAHolder);
            HORARIO.setText(HORARIOHOLDER);
            LOCAL.setText(LOCALHOLDER);
            ENDERECO.setText(ENDERECOHOLDER);
            OBSERVACAO.setText(OBSERVACAOHOLDER);
            AVALIACAO.setText(AVALIACAOHOLDER+"/5.0");
            STATUS.setText(STATUSHOLDER);

            //AVALIACAO.setText("/5.0");


/*
            RatingBar Rating = (RatingBar) findViewById(R.id.ratingBar);
            Rating.setEnabled(false);
            Rating.setMax(5);
            float number = Float.valueOf(AVALIACAO.getText().toString());
            Rating.setRating(number);
            */



        }


    }

    public void cancelar_diaologo(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Cancelar Serviço !!!");
        alertDialogBuilder.setMessage("Tem certeza ?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Cancelar(TempItem);
            }
        });

        alertDialogBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HistoricoIndividualActivity.this,"Serviço não cancelado",Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setNeutralButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Voltado",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onBackPressed(){
        Intent intent = new Intent(HistoricoIndividualActivity.this,HistoricoServicosActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}



