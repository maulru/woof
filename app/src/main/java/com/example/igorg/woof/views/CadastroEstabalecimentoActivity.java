package com.example.igorg.woof.views;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CadastroEstabalecimentoActivity extends AppCompatActivity {

    private EditText estabelecimentoNome;
    private EditText estabelecimentoTelefone;
    private EditText estabelecimentoCnpj;
    private EditText estabelecimentoEndereco;
    private EditText estabelecimentoNumero;
    private EditText estabelecimentoComplemento;
    private EditText estabelecimentoEmail;
    private EditText estabelecimentoObservacoes;
    private Spinner estabelecimentoTipo;

    Usuario usuario;

    private String server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_estabalecimento);

        Toolbar toolbar_proxima = (Toolbar) findViewById(R.id.proximo_toolbar_estabelecimento);
        setSupportActionBar(toolbar_proxima);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        server = Servidor.getIp();

        usuario= SharedPrefManager.getInstance(this).getUser();

        estabelecimentoNome = (EditText) findViewById(R.id.editCadastroEstabelecimentoNome);
        estabelecimentoTelefone = (EditText) findViewById(R.id.editCadastroEstabelecimentoTelefone);
        estabelecimentoCnpj = (EditText) findViewById(R.id.editCadastroEstabelecimentoCNPJ);
        estabelecimentoEndereco = (EditText) findViewById(R.id.editCadastroEstabelecimentoEndereco);
        estabelecimentoNumero = (EditText) findViewById(R.id.editCadastroEstabelecimentoNumero);
        estabelecimentoComplemento = (EditText) findViewById(R.id.editCadastroEstabelecimentoComplemento);
        estabelecimentoEmail = (EditText) findViewById(R.id.editCadastroEstabelecimentoEmail);
        estabelecimentoObservacoes = (EditText) findViewById(R.id.editCadastroEstabelecimentoObservacoes);
        estabelecimentoTipo = (Spinner) findViewById(R.id.spinnerTipoEstabelecimento);

        Spinner tipoEstabelecimento = (Spinner) findViewById(R.id.spinnerTipoEstabelecimento);

        estabelecimentoTelefone.addTextChangedListener(Mask.insert("(##)#####-####", estabelecimentoTelefone));
        estabelecimentoCnpj.addTextChangedListener(Mask.insert("##.###.###/####-##", estabelecimentoCnpj));

        final List<String> tiposEstabelecimentos = new ArrayList<>(Arrays.asList("Organização Não Governamental", "Clínica Veterinária", "Petshop"));


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tiposEstabelecimentos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoEstabelecimento.setAdapter(dataAdapter);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario2, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_okk:
                View contextView = findViewById(R.id.menu_formulario_okk);

                String cnpj = estabelecimentoCnpj.getText().toString();
                cnpj = cnpj.replace("-","");
                cnpj= cnpj.replace(".","");
                if(estabelecimentoNome.getText().length() >3 ){
                    if(ValidaCNPJ.isCNPJ((cnpj))){
                        if(estabelecimentoEndereco.getText().length() >5 ){
                            if(estabelecimentoNumero.getText().length() >0 ){
                                if(estabelecimentoEmail.getText().length() >5 ){
                                    if(estabelecimentoObservacoes.getText().length() >=10){
                                        criarEstabelecimento();
                                    }else{
                                        Snackbar.make(contextView,"Você deve fornecer mais informações em observações",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                        estabelecimentoObservacoes.requestFocus();
                                        break;
                                    }
                                }else{
                                    Snackbar.make(contextView,"Você deve digitar um email",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                    estabelecimentoEmail.requestFocus();
                                    break;
                                }
                            }else{
                                Snackbar.make(contextView,"Você deve digitar um número",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                estabelecimentoNumero.requestFocus();
                                break;
                            }
                        }else{
                            Snackbar.make(contextView,"Você deve digitar um endereço",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                            estabelecimentoEndereco.requestFocus();
                            break;
                        }

                    }else{
                        Snackbar.make(contextView,"Você deve digitar um CNPJ",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        estabelecimentoCnpj.requestFocus();
                        break;
                    }
                }else{
                    Snackbar.make(contextView,"Você deve digitar um nome",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    estabelecimentoNome.requestFocus();
                    break;
                }



        }


        return super.onOptionsItemSelected(item);
    }

    private void criarEstabelecimento() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_estabelecimento.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Log.d("Response", response);
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
                params.put("estabelecimento_complemento", estabelecimentoComplemento.getText().toString());
                params.put("estabelecimento_numero",estabelecimentoNumero.getText().toString());
                params.put("estabelecimento_cep",estabelecimentoComplemento.getText().toString());
                params.put("estabelecimento_telefone",estabelecimentoTelefone.getText().toString());
                params.put("estabelecimento_observacoes",estabelecimentoObservacoes.getText().toString());
                params.put("estabelecimento_email", estabelecimentoEmail.getText().toString());
                params.put("estabelecimento_responsavel", usuario.getId());

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


}
