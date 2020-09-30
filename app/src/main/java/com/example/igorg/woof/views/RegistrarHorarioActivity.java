package com.example.igorg.woof.views;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.masks.Mask;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class RegistrarHorarioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TimePicker picker;
    Button btnGet,dataEscolhe,registrar;
    TextView tvw;
    EditText dataEdit,hora,minuto;

    String server;

    private TextView dateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registrar_horario);

        server = Servidor.getIp();

        registrar = (Button) findViewById(R.id.registrar_horario);
        tvw=(TextView)findViewById(R.id.textView1);
        dataEdit = (EditText) findViewById(R.id.editHorarioData);
        dataEscolhe = (Button) findViewById(R.id.date_dia);

        hora = (EditText) findViewById(R.id.editHoraRegistra);
        minuto = (EditText) findViewById(R.id.editMinutoRegistra);

        final EditText campo_data = (EditText) findViewById(R.id.editHorarioData);
        campo_data.addTextChangedListener(Mask.insert("##/##/####", campo_data));

        final EditText campo_hora = (EditText) findViewById(R.id.editHoraRegistra);
        campo_hora.addTextChangedListener(Mask.insert("##", campo_hora));

        final EditText campo_minuto = (EditText) findViewById(R.id.editMinutoRegistra);
        campo_minuto.addTextChangedListener(Mask.insert("##", campo_minuto));

        dateText = findViewById(R.id.date_text);

        findViewById(R.id.date_dia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_horario(view);
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


        /*
        if(month < 9) {
            String date =  dayOfMonth + "/0" + (month+1) + "/" + year;
            dataEdit.setText(date);
        }



        else
        {
            String date =  dayOfMonth + "/" + (month+1) + "/" + year;
            dataEdit.setText(date);
        }
*/
    }





    public void registrar_horario(View v){
        Log.i("Empresa","ID:"+estabelecimentoId);
        final String hora_final = hora.getText()+":"+minuto.getText();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_horario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");


                    if(success.equals("1")) {

                        tvw.setText("");
                        dateText.setText("");

                        Toast.makeText(getApplicationContext(), "Horario Cadastrado", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(getApplicationContext(),"Erro no registro", Toast.LENGTH_SHORT).show();
                    }
                }

                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("horario",hora_final);
                params.put("dia",dataEdit.getText().toString());
                params.put("id_estabelecimento",String.valueOf(estabelecimentoId));
                params.put("tipo","Consulta");
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


