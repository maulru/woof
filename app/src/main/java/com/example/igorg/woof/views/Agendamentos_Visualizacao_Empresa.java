package com.example.igorg.woof.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.masks.Mask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class Agendamentos_Visualizacao_Empresa extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText dataEdit;
    Button data;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamentos__visualizacao__empresa);

        final EditText campo_data = (EditText) findViewById(R.id.dia);
        campo_data.addTextChangedListener(Mask.insert("##/##/####", campo_data));
        dataEdit = (EditText) findViewById(R.id.dia);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/YYYY");
        String strDate = mdformat.format(calendar.getTime());
        TextView textView = (TextView) findViewById(R.id.dia);
        textView.setText(strDate);


        data =(Button)findViewById(R.id.date_dia);

        findViewById(R.id.date_dia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        findViewById(R.id.pesquisar_horarios).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisar();
            }
        });


    }

    public void showDatePickerDialog(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(

                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String day="",mes="";

        if(dayOfMonth<10){
            day = "0"+String.valueOf(dayOfMonth);
        }else{
            day = String.valueOf(dayOfMonth);
        }

        if(month<10){
            mes= "0"+String.valueOf(month+1);
        }else{
            mes = String.valueOf(month+1);
        }

        String date = day+mes+year;
        dataEdit.setText("");
        dataEdit.setText(date);
    }



    public void pesquisar(){
        progressDialog = ProgressDialog.show(Agendamentos_Visualizacao_Empresa.this,"Verificando horarios","Por favor aguarde",false,false);


        Handler pesquisando = new Handler();


        pesquisando.postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDialog.dismiss();
                Intent intent = new Intent(Agendamentos_Visualizacao_Empresa.this, Pesquisa_AgendamentoActivity.class);
                intent.putExtra("dia", dataEdit.getText().toString());
                startActivity(intent);
            }
        }, 2000);

    }


}

