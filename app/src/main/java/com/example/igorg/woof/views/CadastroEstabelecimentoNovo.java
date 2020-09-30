package com.example.igorg.woof.views;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.masks.Mask;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;
import com.example.igorg.woof.voids.ValidaCNPJ;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.estabelecimentoId;

public class CadastroEstabelecimentoNovo extends AppCompatActivity {

    private EditText estabelecimentoNome;
    private EditText estabelecimentoTelefone;
    private EditText estabelecimentoCnpj;
    private EditText estabelecimentoEndereco;
    private EditText estabelecimentoNumero;
    private EditText estabelecimentoComplemento;
    private EditText estabelecimentoEmail;
    private EditText estabelecimentoObservacoes;
    private Spinner estabelecimentoTipo;
    private Button estabelecimentoCadastra;
    Usuario usuario;
    private TextToSpeech mTTS;
    String GetImageNameFromEditText, enderecoEnvia, obsEnvia, latEnvia, longEnvia;
    EditText enderecoET, observacaoET;

    String LAT = "Latitude";
    String LONG = "Longitude";

    private String server;
    TextView addressTV;
    TextView latLongTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estabelecimento_novo);

        server = Servidor.getIp();

        usuario= SharedPrefManager.getInstance(this).getUser();

        estabelecimentoNome = (EditText) findViewById(R.id.editCadastroEstabelecimentoNome);
        estabelecimentoTelefone = (EditText) findViewById(R.id.editCadastroEstabelecimentoTelefone);
        estabelecimentoCnpj = (EditText) findViewById(R.id.editCadastroEstabelecimentoCNPJ);
        estabelecimentoEndereco = (EditText) findViewById(R.id.editCadastroEstabelecimentoEndereco);
        estabelecimentoEmail = (EditText) findViewById(R.id.editCadastroEstabelecimentoEmail);
        estabelecimentoTipo = (Spinner) findViewById(R.id.spinnerTipoEstabelecimento);

        estabelecimentoCadastra = (Button) findViewById(R.id.butt_cadastra_estab);

        addressTV = (TextView) findViewById(R.id.addressTV);
        latLongTV = (TextView) findViewById(R.id.latLongTV);
        enderecoET = (EditText)findViewById(R.id.editCadastroEstabelecimentoEndereco);



        Spinner tipoEstabelecimento = (Spinner) findViewById(R.id.spinnerTipoEstabelecimento);

        estabelecimentoTelefone.addTextChangedListener(Mask.insert("(##)#####-####", estabelecimentoTelefone));
        estabelecimentoCnpj.addTextChangedListener(Mask.insert("##.###.###/####-##", estabelecimentoCnpj));

        final List<String> tiposEstabelecimentos = new ArrayList<>(Arrays.asList("Clínica Veterinária", "Petshop"));


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tiposEstabelecimentos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoEstabelecimento.setAdapter(dataAdapter);

        estabelecimentoCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
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

    private void check() {

        View contextView = findViewById(R.id.butt_cadastra_estab);

        String cnpj = estabelecimentoCnpj.getText().toString();
        cnpj = cnpj.replace("-","");
        cnpj= cnpj.replace(".","");
        cnpj= cnpj.replace("/","");
        if(estabelecimentoNome.getText().length() >3 ){
            if(ValidaCNPJ.isCNPJ((cnpj))){
                if(estabelecimentoEndereco.getText().length() >5 ){

                    if(estabelecimentoEmail.getText().length() >5 ){

                        // criarEstabelecimento();

                        teste();

                    }else{
                        Snackbar.make(contextView,"Você deve digitar um email",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        estabelecimentoEmail.requestFocus();

                    }

                }else{
                    Snackbar.make(contextView,"Você deve digitar um endereço",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    estabelecimentoEndereco.requestFocus();

                }

            }else{
                Snackbar.make(contextView,"Você deve digitar um CNPJ",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                estabelecimentoCnpj.requestFocus();

            }
        }else{
            Snackbar.make(contextView,"Você deve digitar um nome",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            estabelecimentoNome.requestFocus();

        }

    }


    public void teste() {

        EditText editText = (EditText) findViewById(R.id.editCadastroEstabelecimentoEndereco);
        String address = editText.getText().toString();


        GeocodingLocation1 locationAddress = new GeocodingLocation1();
        locationAddress.getAddressFromLocation1(address,
                getApplicationContext(), new CadastroEstabelecimentoNovo.GeocoderHandler());



        Handler criar = new Handler();


        criar.postDelayed(new Runnable() {
            @Override
            public void run() {
                criarEstabelecimento();

            }
        }, 2000);

    }

    private void criarEstabelecimento() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_estabelecimento_novo.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("Response", response);

                try{
                    JSONObject obj = new JSONObject(response);

                    estabelecimentoId = Integer.parseInt(obj.getString("ID_Estabelecimento"));

                    Log.d("ID do estabelecimento:", String.valueOf(estabelecimentoId));
                } catch (JSONException e) {
                    e.printStackTrace();

                    Log.i("Erro Json","erro:"+e);
                }

                Intent cadastro = new Intent(CadastroEstabelecimentoNovo.this,
                        CadastroEstabelecimentoFoto.class);
                startActivity(cadastro);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"erro: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("estabelecimento_nome",estabelecimentoNome.getText().toString());
                params.put("estabelecinemto_cnpj",estabelecimentoCnpj.getText().toString());
                params.put("estabelecimento_tipo",estabelecimentoTipo.getSelectedItem().toString());
                params.put("estabelecimento_endereco", estabelecimentoEndereco.getText().toString());
                params.put("estabelecimento_telefone",estabelecimentoTelefone.getText().toString());
                params.put("estabelecimento_email", estabelecimentoEmail.getText().toString());
                params.put("latitude", latEnvia);
                params.put("longitude", longEnvia);
                params.put("estabelecimento_responsavel", usuario.getId());
                params.put("usuario_rede", String.valueOf(usuario.getMedia()));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }



    public class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String latitude;
            String longitude;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    latitude = bundle.getString("lat");
                    longitude = bundle.getString("long");
                    break;
                default:
                    latitude = null;
                    longitude = null;
            }
            addressTV.setText(latitude);
            latLongTV.setText(longitude);
            latEnvia = addressTV.getText().toString();
            longEnvia = latLongTV.getText().toString();

        }
    }
    public void falar() {
        mTTS.speak("Cadastre seu Estabelecimento", TextToSpeech.QUEUE_FLUSH, null);
    }


}
