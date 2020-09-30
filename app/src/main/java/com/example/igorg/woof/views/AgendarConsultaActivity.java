package com.example.igorg.woof.views;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class AgendarConsultaActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener{


    public static final String Horarios = "Horario";
    public static final String Pets = "Nome";
    public static final String Id  = "Id";
    public static final String JSON_ARRAY = "result";
    private JSONArray result1;
    private JSONArray result11;
    Spinner spinner;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList1;
    TextView id_txt,horario_txt,id_estabelecimento,id_pet;
    String date;
    private TextView dateText;
    TextView nome,endereco_txt,tel;
    public static final String ID_PET = "ID_Animal";
    String nome1,telefone,id,endereco1;
    String server;
    ArrayAdapter<String> adapter;
    Spinner spinner_horario;
    Spinner spinner_pet;
    public static final String Id_ESTABELECIMENTO ="ID_Estabelecimento";
    public static final String ENDERECO = "Endereco";
    ArrayAdapter<String> adapter_pet;
    ArrayAdapter<String> adapter_horario;
    public static final String TITLE = "Nome";
    String php_pet;
    String php_horario;
    String php_horario_filtro;
    String[] data_pet;
    String[] data_horario;
    ArrayAdapter<String> contactAdapter;
    String[] contactArray = {};
    InputStream is=null;
    String line = null;
    String result=null;

    String spinnerText;

    TextView textview;


    Intent intent;

    EditText  endereco, observacao;

    TextView data,tipo, horario,id_horario,pet;


    String horario_spinner, pet_spinner;
    String NomeHolder, EnderecoHolder;
    TextView TELEFONE;
    EditText NOME;

    Usuario usuario;


    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_consulta);

        usuario= SharedPrefManager.getInstance(this).getUser();
        server = Servidor.getIp();

        php_pet = "http://"+server+"/woof/pet.php";
        php_horario = "http://"+server+"/woof/horario.php";
        php_horario_filtro= "http://"+server+"/woof/horario.php";

        NOME = (EditText)findViewById(R.id.NomeEstabelecimento);
        NOME.setEnabled(false);
        //ENDERECO = (TextView)findViewById(R.id.Endereco);

        NomeHolder = getIntent().getStringExtra("nome");

        NOME.setText(NomeHolder);

        spinner_pet = findViewById(R.id.spinnerPet);
        spinner= (Spinner) findViewById(R.id.spinnerHorario);
        horario_txt= (TextView) findViewById(R.id.horario_txt);
        id_txt= (TextView) findViewById(R.id.id_horario);
        id_pet= (TextView) findViewById(R.id.id_pet);
        endereco_txt = (TextView)findViewById(R.id.Endereco);
        id_estabelecimento = (TextView)findViewById(R.id.id_estabelecimento);
        pet= (TextView)findViewById(R.id.pet_txt);



        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));



        teste_dia();
        pet();





        dateText = findViewById(R.id.date_text);

        findViewById(R.id.show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



        tipo = (TextView)findViewById(R.id.TipoServico);
        nome = (EditText)findViewById(R.id.NomeEstabelecimento);
        endereco = (EditText)findViewById(R.id.Endereco);
        endereco.setEnabled(false);
        observacao = (EditText)findViewById(R.id.observacao);
        data = (TextView)findViewById(R.id.date_text);
        horario= (TextView)findViewById(R.id.horario_txt);
        id_horario= (TextView)findViewById(R.id.id_horario);



        Button b = (Button) findViewById(R.id.Enviar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),ConfirmarAgendamentoActivity.class);
                intent.putExtra("Tipo", tipo.getText().toString());
                intent.putExtra("Nome", nome.getText().toString());
                intent.putExtra("Endereco", endereco.getText().toString());
                intent.putExtra("Observacao", observacao.getText().toString());
                intent.putExtra("Data", data.getText().toString());
                intent.putExtra("horario", horario.getText().toString());
                intent.putExtra("id_horario", id_horario.getText().toString());
                intent.putExtra("pet", pet.getText().toString());
                intent.putExtra("id_estabelecimeto", id_estabelecimento.getText().toString());
                intent.putExtra("id_pet", id_pet.getText().toString());
                intent.putExtra("id_usuario",usuario.getId());
                intent.putExtra("servico","Consulta");
                startActivity(intent);
            }
        });



        arrayList1 = new ArrayList<String>();

        spinner_pet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
                pet.setText(""+getNome(position));
                id_pet.setText(""+getId1(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //horario_txt.setText("");
                id_pet.setText("");
            }
        });




        arrayList = new ArrayList<String>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
                horario_txt.setText(""+getHorario(position));
                id_txt.setText(""+getId(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                horario_txt.setText("");
                id_txt.setText("");
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
        if(dayOfMonth >= 10 && month < 9) {
            date =  dayOfMonth + "/0" + (month+1) + "/" + year;
            dateText.setText(date);
        }
        if(dayOfMonth <9 && month>9)
        {
            date =  "0" + dayOfMonth + "/" + (month+1) + "/" + year;
            dateText.setText(date);
        }
        else
        {
            date =  dayOfMonth + "/" + (month+1) + "/" + year;
            dateText.setText(date);
        }


        horario();
    }

    public void horario(){
        Log.i("Estabelecimento","ID:"+estabelecimentoId);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/getData.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        Toast.makeText(getApplicationContext(), "Sucesso!: " + response,Toast.LENGTH_SHORT).show();
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result1 = j.getJSONArray(JSON_ARRAY);
                            array(result1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(getApplicationContext(), "erro de resposta 2: " + error,Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dia",dateText.getText().toString());
                params.put("id_estabelecimento",String.valueOf(estabelecimentoId));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }


    private void array(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList.add(json.getString(Horarios));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        spinner.setAdapter(new ArrayAdapter<String>(AgendarConsultaActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
    }




    //Method to get student name of a particular position
    private String getHorario(int position){
        String horario="";
        try {
            //Getting object of given index
            JSONObject json = result1.getJSONObject(position);

            //Fetching name from that object
            horario = json.getString(Horarios);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Returning the name
        return horario;

    }

    //Doing the same with this method as we did with getName()
    private String getId(int position){
        String id="";
        try {
            JSONObject json = result1.getJSONObject(position);
            id = json.getString(Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }



////////////////////////////////////////////////////

    public void pet(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/getPet.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response_PET", response);
                        //Toast.makeText(getApplicationContext(), "Sucesso!: " + response,Toast.LENGTH_SHORT).show();
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            result11 = j.getJSONArray(JSON_ARRAY);
                            array1(result11);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(getApplicationContext(), "erro de resposta 2: " + error,Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("h",usuario.getId());
                params.put("usuario_rede",String.valueOf(usuario.getMedia()));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void array1(JSONArray j) {

        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList1.add(json.getString(Pets));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        spinner_pet.setAdapter(new ArrayAdapter<String>(AgendarConsultaActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList1));
    }





    private String getNome(int position){
        String Nome="";
        try {
            //Getting object of given index
            JSONObject json = result11.getJSONObject(position);

            //Fetching name from that object
            Nome = json.getString(Pets);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Returning the name
        return Nome;

    }

    //Doing the same with this method as we did with getName()
    private String getId1(int position){
        String id="";
        try {
            JSONObject json = result11.getJSONObject(position);
            id = json.getString(ID_PET);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }















    public void teste_dia(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/mapa_nome.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response: ", response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String getObject = jObj.getString("estabelecimento");
                            JSONArray jsonArray = new JSONArray(getObject);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                nome1 = jsonObject.getString(TITLE);
                                //telefone = jsonObject.getString(TELEFONE);
                                endereco1 = jsonObject.getString(ENDERECO);
                                id= jsonObject.getString(Id_ESTABELECIMENTO);


                                id_estabelecimento.setText(id);
                                endereco_txt.setText(endereco1);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "erro de resposta 2: " + error,Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome",nome.getText().toString());

                return params;
            }




        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }

}

