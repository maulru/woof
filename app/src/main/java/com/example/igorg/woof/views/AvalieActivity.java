package com.example.igorg.woof.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;


public class AvalieActivity extends AppCompatActivity {
    private RatingBar rBar;
    private TextView tView;
    private Button btn;

    String HttpURL,server;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText Observacao;
    Button Update;
    String IdHolder, ObservacaoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalie);
        rBar = (RatingBar) findViewById(R.id.ratingBar1);
        tView = (TextView) findViewById(R.id.nota);
        btn = (Button)findViewById(R.id.btnGet);

        server = Servidor.getIp();

         HttpURL = "http://"+server+"/woof/updateRting.php";

        RatingBar Rating = (RatingBar) findViewById(R.id.ratingBar1);
        Rating.setMax(5);
        Rating.setRating(Float.parseFloat("3.0"));
        Rating.setPadding(10, 0, 0, 0);




        tView = (TextView)findViewById(R.id.nota);


        Update = (Button)findViewById(R.id.UpdateButton);


        IdHolder = getIntent().getStringExtra("Id");
        ObservacaoHolder = getIntent().getStringExtra("Rating");




        tView.setText(ObservacaoHolder);


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int noofstars = rBar.getNumStars();
                float getrating = rBar.getRating();
                tView.setText("" + getrating);

                Handler nota = new Handler();
                nota.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetDataFromEditText();
                        StudentRecordUpdate(IdHolder,ObservacaoHolder);


                    }
                }, 2000);

            }
        });

    }




    public void GetDataFromEditText(){

        ObservacaoHolder = tView.getText().toString();


    }


    public void StudentRecordUpdate(final String ID, final String Obs){

        class StudentRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(AvalieActivity.this,"Carregando",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(AvalieActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);

                hashMap.put("Rating",params[1]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        StudentRecordUpdateClass studentRecordUpdateClass = new StudentRecordUpdateClass();

        studentRecordUpdateClass.execute(ID,Obs);
    }




}