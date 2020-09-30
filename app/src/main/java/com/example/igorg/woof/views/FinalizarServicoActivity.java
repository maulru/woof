package com.example.igorg.woof.views;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;

import java.util.HashMap;

public class FinalizarServicoActivity extends AppCompatActivity {

    String server;
    String HttpURL;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText Detalhes;
    Button Finalizar;
    String IdHolder, DETALHESHOLDER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_servico);

        Detalhes = (EditText)findViewById(R.id.editName);


        Finalizar = (Button)findViewById(R.id.finalizar);


        IdHolder = getIntent().getStringExtra("Id");
        DETALHESHOLDER = getIntent().getStringExtra("detalhes");

        server = Servidor.getIp();

        HttpURL = "http://"+server+"/woof/finalizar_atendimento.php";

        Detalhes.setText(DETALHESHOLDER);


        Finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                GetDataFromEditText();


                finalizar(IdHolder,DETALHESHOLDER);

            }
        });


    }


    public void GetDataFromEditText(){

        DETALHESHOLDER = Detalhes.getText().toString();


    }


    public void finalizar(final String ID, final String Obs){

        class finalizarClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(FinalizarServicoActivity.this,"Carregando",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(FinalizarServicoActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);

                hashMap.put("detalhes_atendimento",params[1]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        finalizarClass detalhes = new finalizarClass();

        detalhes.execute(ID,Obs);
    }
}
