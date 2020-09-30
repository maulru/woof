package com.example.igorg.woof.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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
import com.example.igorg.woof.voids.ValidaCPF;
import com.example.igorg.woof.volley.VolleySingleton;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText usuarioNome;
    private EditText usuarioEmail;
    private EditText usuarioCPF;
    private EditText usuarioNascimento;
    private EditText usuarioSenha;
    private EditText usuarioSenhaConfirmacao;
    private RadioGroup usuarioSexo;
    static String server;
    private String sexo;
    private AlertDialog alerta;
    private String email, cpf, password,confirmpassword,ddn,name;
    Activity mContext = this;
    String usuario_id;

    private static CadastroUsuarioActivity sInstance;

    public static CadastroUsuarioActivity getInstance(){
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        //definindo ip do servidor
        server = (String) Servidor.getIp();


        //Inicialização dos componentes da tela
        usuarioNome = (EditText) findViewById(R.id.textCadastroNome);
        usuarioEmail = (EditText)findViewById(R.id.textCadastroEmail);
        usuarioCPF = (EditText) findViewById(R.id.textCadastroCPF);
        usuarioNascimento = (EditText) findViewById(R.id.dateCadastroNascimento);
        usuarioSenha = (EditText) findViewById(R.id.textCadastroSenha);
        usuarioSenhaConfirmacao = (EditText) findViewById(R.id.textCadastroConfirmaSenha);
        usuarioSexo = (RadioGroup) findViewById(R.id.radioGroupSexoUsuario);

        //Início das máscaras de InputView
        final EditText campo_data_nascimento = (EditText) findViewById(R.id.dateCadastroNascimento);
        campo_data_nascimento.addTextChangedListener(Mask.insert("##/##/####", campo_data_nascimento));

        final EditText campo_cpf = (EditText) findViewById(R.id.textCadastroCPF);
        campo_cpf.addTextChangedListener(Mask.insert("###.###.###-##", campo_cpf));
        //Fim das máscaras de InputView /**/

        //Definindo Toolbar e removendo titulo
        Toolbar toolbar_proxima = (Toolbar) findViewById(R.id.proximo_toolbar);
        setSupportActionBar(toolbar_proxima);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSharedPreferences("user_preferences", MODE_PRIVATE);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                View contextView = findViewById(R.id.menu_formulario_ok);

                //Verificar se os campos estão preenchidos e se as senhas coincidem
                password = usuarioSenha.getText().toString();
                confirmpassword = usuarioSenhaConfirmacao.getText().toString();
                cpf = usuarioCPF.getText().toString();
                cpf = cpf.replace(".","");
                cpf = cpf.replace("-","");
                name = usuarioNome.getText().toString();

                if (usuarioNome.getText().length() > 0){
                    if(usuarioEmail.getText().length() > 0){
                        if(usuarioNascimento.getText().length() >=8){
                            if(ValidaCPF.isCPF(cpf)){
                                if(usuarioSenha.getText().length() >= 8){
                                    if(password.equals(confirmpassword)){

                                        sexo = ((RadioButton)findViewById(usuarioSexo.getCheckedRadioButtonId())).getText().toString();

                                        Toast.makeText(getApplicationContext(),"Todas as checagens foram feitas", Toast.LENGTH_SHORT).show();
                                        registra_usuario();

                                    }else{
                                        Snackbar.make(contextView,"As senhas não coincidem",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                        usuarioSenha.requestFocus();
                                        break;
                                    }
                                }else{
                                    Snackbar.make(contextView,"Insira uma senha",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                    usuarioSenha.requestFocus();
                                    break;
                                }
                            }else{
                                Snackbar.make(contextView,"CPF inválido",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                usuarioCPF.requestFocus();
                                break;
                            }
                        }else{
                            Snackbar.make(contextView,"Insira sua data de nascimento",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                            usuarioNascimento.requestFocus();
                            break;
                        }
                    }else{
                        Snackbar.make(contextView,"Insira um email",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        usuarioEmail.requestFocus();
                        break;
                    }
                }else{
                    Snackbar.make(contextView,"Insira um nome",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    usuarioNome.requestFocus();
                    break;
                }






                break;

        }


        return super.onOptionsItemSelected(item);
    }



    public void registra_usuario(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_usuario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("responseString","response:"+response);
                        try {
                            //convertendo a resposta em um objeto json
                            JSONObject obj = new JSONObject(response);

                            //se não houver nenhum erro de resposta

                            Toast.makeText(getApplicationContext(), "Sem erro de reposta", Toast.LENGTH_SHORT).show();



                            //criando um objeto novo usuário
                            Usuario user = new Usuario(
                                    obj.getString("usuario_id"),
                                    obj.getString("usuario_nome"),
                                    obj.getString("usuario_email")
                            );

                            //armazenando o usuário na sharedpreferences
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                            //iniciando a activity de perfil
                            finish();
                            Toast.makeText(getApplicationContext(),"id:"+ obj.getInt("usuario_id"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro de resposta 2", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nome",usuarioNome.getText().toString());
                params.put("email",usuarioEmail.getText().toString());
                params.put("cpf",usuarioCPF.getText().toString());
                params.put("data_nascimento", usuarioNascimento.getText().toString());
                params.put("senha",usuarioSenha.getText().toString());
                params.put("sexo",sexo);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }







    public void registrar_usuario(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_usuario.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pegarId();

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
                params.put("nome",usuarioNome.getText().toString());
                params.put("email",usuarioEmail.getText().toString());
                params.put("cpf",usuarioCPF.getText().toString());
                params.put("data_nascimento", usuarioNascimento.getText().toString());
                params.put("senha",usuarioSenha.getText().toString());
                params.put("sexo",sexo);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);



    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), HomeUserActivity.class);
        startActivity(i);
        finish();

    }

    private void pegarId(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://"+server+"/woof/busca_usuario.php?email="+usuarioEmail.getText().toString()+"", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    usuario_id = jsonObject.getString("usuario_id");


                    Toast.makeText(getApplicationContext(),"Id do usuário criado: " + usuario_id, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                android.support.v7.app.AlertDialog.Builder alert;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null){
                    switch (response.statusCode){

                        case 400:
                            alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                            alert.setTitle("Error");
                            alert.setMessage("The request could not be understood by the server due to malformed syntax");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                            break;
                        case 404:
                            alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                            alert.setTitle("Error");
                            alert.setMessage("The server has not found anything matching the Request-URI");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                            break;
                        case 403:
                            alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                            alert.setTitle("Error");
                            alert.setMessage("The server understood the request, but is refusing to fulfill it");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                            break;


                    }
                }
                else{
                    alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                    alert.setTitle("Error");
                    alert.setMessage(error.toString());
                    alert.setCancelable(false);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("Accept","Application/json; charset=UTF-8");
                return params;
            }


        };
        VolleySingleton.getInstance().addRequestQueue(stringRequest);
    }

}



