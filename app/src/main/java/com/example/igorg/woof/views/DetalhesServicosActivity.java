package com.example.igorg.woof.views;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;

import java.util.HashMap;

public class DetalhesServicosActivity extends AppCompatActivity {


    String HttpURL ,server;
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    TextView Observacao;
    Button Update;
    String IdHolder, ObservacaoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_servicos);

        Observacao = (TextView)findViewById(R.id.detalhes);

        server = Servidor.getIp();
        HttpURL= "http://"+server+"/woof/update_historico.php";



        IdHolder = getIntent().getStringExtra("Id");
        ObservacaoHolder = getIntent().getStringExtra("detalhes");

        Observacao.setText(ObservacaoHolder);



    }


    public void GetDataFromEditText(){

        ObservacaoHolder = Observacao.getText().toString();


    }


    public void StudentRecordUpdate(final String ID, final String Obs){

        class StudentRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(DetalhesServicosActivity.this,"Carregando",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(DetalhesServicosActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);

                hashMap.put("Horario",params[1]);


                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        StudentRecordUpdateClass studentRecordUpdateClass = new StudentRecordUpdateClass();

        studentRecordUpdateClass.execute(ID,Obs);
    }
}
