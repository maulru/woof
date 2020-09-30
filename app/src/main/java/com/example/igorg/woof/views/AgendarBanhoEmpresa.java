package com.example.igorg.woof.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.modelo.Servidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoEndereco;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;
import static com.example.igorg.woof.modelo.Servidor.estabelecimentoNome;

public class AgendarBanhoEmpresa extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String Horarios = "Horario";
    public static final String Pets = "Nome";
    public static final String Id = "Id";
    public static final String JSON_ARRAY = "result";
    public static final String Tipo = "Tipo";
    private JSONArray result1;
    private JSONArray result11;
    Spinner spinner;
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList1;
    TextView id_txt, horario_txt, id_estabelecimento, id_pet;
    String date;
    private TextView dateText;
    TextView nome, endereco_txt, tel;
    public static final String ID_PET = "ID_Animal";
    String nome1, telefone, id, endereco1;
    String server;
    ArrayAdapter<String> adapter;
    Spinner spinner_pet;
    public static final String Id_ESTABELECIMENTO = "ID_Estabelecimento";
    public static final String ENDERECO = "Endereco";
    public static final String TITLE = "Nome";
    String php_pet;
    String php_horario;
    String php_horario_filtro;
    InputStream is = null;
    String result = null;

    TextView textview;


    Intent intent;

    EditText endereco, observacao;

    TextView data, tipo, horario, id_horario, pet,tipo_servico;


    EditText Email_usuario;

    String usuario_email, url_busca_email, id_usuario,servico;

    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;

    Button checar, enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_banho_empresa);

        server = Servidor.getIp();

        id_usuario = "";

        php_pet = "http://" + server + "/woof/pet.php";
        php_horario = "http://" + server + "/woof/horario.php";
        php_horario_filtro = "http://" + server + "/woof/horario.php";

        Email_usuario = (EditText) findViewById(R.id.email_usuario_petshop);
        //ENDERECO = (TextView)findViewById(R.id.Endereco);

        spinner_pet = findViewById(R.id.spinnerPet_petshop);
        spinner = (Spinner) findViewById(R.id.spinnerHorario_petshop);
        horario_txt = (TextView) findViewById(R.id.horario_txt_petshop);
        id_txt = (TextView) findViewById(R.id.id_horario_petshop);
        id_pet = (TextView) findViewById(R.id.id_pet_petshop);
        id_estabelecimento = (TextView) findViewById(R.id.id_estabelecimento_petshop);
        pet = (TextView) findViewById(R.id.pet_txt_petshop);
        tipo_servico = (TextView) findViewById(R.id.txt_petshop_servico);

        servico="Banho";

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));


        teste_dia();


        dateText = findViewById(R.id.date_text_petshop);

        findViewById(R.id.show_dialog_petshop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        tipo = (TextView) findViewById(R.id.TipoServico_petshop);

        observacao = (EditText) findViewById(R.id.observacao_petshop);
        data = (TextView) findViewById(R.id.date_text_petshop);
        horario = (TextView) findViewById(R.id.horario_txt_petshop);
        id_horario = (TextView) findViewById(R.id.id_horario_petshop);


        enviar = (Button) findViewById(R.id.Enviar_petshop);

        enviar.setEnabled(false);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Checar();

            }
        });

        checar = (Button) findViewById(R.id.butt_checar_email_petshop);
        checar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaEmail();
            }
        });

        arrayList1 = new ArrayList<String>();

        spinner_pet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
                pet.setText("" + getNome(position));
                id_pet.setText("" + getId1(position));

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
                horario_txt.setText("" + getHorario(position));
                id_txt.setText("" + getId(position));
                tipo_servico.setText(""+getTipo(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                horario_txt.setText("");
                id_txt.setText("");
            }
        });


    }

    private void verificaEmail() {


        url_busca_email = "http://" + server + "/woof/busca_email.php?h=" + Email_usuario.getText().toString().toLowerCase();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_busca_email, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Sucesso", "JSONObject: " + response);

                        habilita_campos(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Usuário não cadastrado", Toast.LENGTH_SHORT).show();
                Log.i("ResponseError", "Erro: " + error);
            }
        });


        requestQueue = Volley.newRequestQueue(AgendarBanhoEmpresa.this);

        requestQueue.add(jsonObjectRequest);

    }

    private void habilita_campos(JSONObject response) {

        JSONObject json = response;

        try {
            Log.i("Json_response", "Response:" + json);
            if (json.getString("Email").contentEquals(Email_usuario.getText().toString().toLowerCase())) {

                enviar.setEnabled(true);
                checar.setEnabled(false);
                Email_usuario.setEnabled(false);
                id_usuario = json.getString("ID_Usuario");
                pet();
                Toast.makeText(getApplicationContext(), "Usuário encontrado!", Toast.LENGTH_SHORT).show();

            } else {

            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }


    public void showDatePickerDialog() {

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
        if (dayOfMonth >= 10 && month < 9) {
            date = dayOfMonth + "/0" + (month + 1) + "/" + year;
            dateText.setText(date);
        }
        if (dayOfMonth < 9 && month > 9) {
            date = "0" + dayOfMonth + "/" + (month + 1) + "/" + year;
            dateText.setText(date);
        } else {
            date = dayOfMonth + "/" + (month + 1) + "/" + year;
            dateText.setText(date);
        }


        horario();
    }

    public void horario() {
        Log.i("Estabelecimento", "ID:" + estabelecimentoId);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + server + "/woof/getData.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("Response", response);
                        Toast.makeText(getApplicationContext(), "Sucesso!: " + response, Toast.LENGTH_SHORT).show();
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
                params.put("dia", dateText.getText().toString());
                params.put("id_estabelecimento", String.valueOf(estabelecimentoId));

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
                arrayList.add(json.getString(Horarios)+"-"+json.getString(Tipo));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        spinner.setAdapter(new ArrayAdapter<String>(AgendarBanhoEmpresa.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
    }

    private String getTipo(int position){
        String servico_tipo="";
        try {
            JSONObject json = result1.getJSONObject(position);
            servico_tipo = json.getString(Tipo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return servico_tipo;
    }

    //Method to get student name of a particular position
    private String getHorario(int position) {
        String horario = "";
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
    private String getId(int position) {
        String id = "";
        try {
            JSONObject json = result1.getJSONObject(position);
            id = json.getString(Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }


////////////////////////////////////////////////////

    public void pet() {
        Log.i("ID_Usuario", "ID:" + id_usuario);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + server + "/woof/getPet_simplificado.php",
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
                params.put("h", id_usuario);

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


        spinner_pet.setAdapter(new ArrayAdapter<String>(AgendarBanhoEmpresa.this, android.R.layout.simple_spinner_dropdown_item, arrayList1));
    }


    private String getNome(int position) {
        String Nome = "";
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
    private String getId1(int position) {
        String id = "";
        try {
            JSONObject json = result11.getJSONObject(position);
            id = json.getString(ID_PET);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }


    public void teste_dia() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + server + "/woof/mapa_nome.php",
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
                                id = jsonObject.getString(Id_ESTABELECIMENTO);


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
                params.put("nome", nome.getText().toString());

                return params;
            }


        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }

    public void Checar() {
        View contextView = findViewById(R.id.Enviar_empresa);
        if (Email_usuario != null) {
            if (horario.getText() != "") {
                if (id_pet.getText() != "") {


                    intent = new Intent(getApplicationContext(), ConfirmarAgendamentoActivity.class);
                    intent.putExtra("Tipo", tipo.getText().toString());
                    intent.putExtra("Nome", estabelecimentoNome);
                    intent.putExtra("Endereco", estabelecimentoEndereco);
                    intent.putExtra("Observacao", observacao.getText().toString());
                    intent.putExtra("Data", data.getText().toString());
                    intent.putExtra("horario", horario.getText().toString());
                    intent.putExtra("id_horario", id_horario.getText().toString());
                    intent.putExtra("pet", pet.getText().toString());
                    intent.putExtra("id_estabelecimeto", String.valueOf(estabelecimentoId));
                    intent.putExtra("id_pet", id_pet.getText().toString());
                    intent.putExtra("id_usuario", String.valueOf(id_usuario));
                    intent.putExtra("servico",tipo_servico.getText().toString());
                    startActivity(intent);


                } else {
                    Snackbar.make(contextView, "Selecione um pet!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    spinner_pet.requestFocus();
                }
            } else {
                Snackbar.make(contextView, "Selecione um horário!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                spinner.requestFocus();
            }
        } else {
            Snackbar.make(contextView, "Informe o email do cliente!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            spinner_pet.requestFocus();
        }


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}