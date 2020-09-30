package com.example.igorg.woof.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.masks.Mask;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;
import com.example.igorg.woof.voids.ValidaCPF;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.usuario_rede;

public class CadastroUsuarioNovo extends AppCompatActivity {

    private EditText usuarioNome;
    private EditText usuarioEmail;
    private EditText usuarioCPF;
    private EditText usuarioNascimento;
    private EditText usuarioSenha;
    private EditText usuarioSenhaConfirmacao;
    private RadioGroup usuarioSexo;
    private Button usuarioCadastra;
    static String server;
    private String sexo;
    private AlertDialog alerta;
    private String email, cpf, password,confirmpassword,ddn,name;
    Activity mContext = this;
    String usuario_id;
    private EditText usuarioEndereco,usuarioTelefone;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario_novo);

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
        usuarioCadastra = (Button) findViewById(R.id.butt_registra_usuario);
        usuarioEndereco = (EditText) findViewById(R.id.textCadastroEndereco);
        usuarioTelefone = (EditText) findViewById(R.id.textCadastroTelefone);

        //Início das máscaras de InputView
        final EditText campo_data_nascimento = (EditText) findViewById(R.id.dateCadastroNascimento);
        campo_data_nascimento.addTextChangedListener(Mask.insert("##/##/####", campo_data_nascimento));

        final EditText campo_cpf = (EditText) findViewById(R.id.textCadastroCPF);
        campo_cpf.addTextChangedListener(Mask.insert("###.###.###-##", campo_cpf));

        final EditText campo_telefone = (EditText) findViewById(R.id.textCadastroTelefone);
        campo_telefone.addTextChangedListener(Mask.insert("(##)#####-####", campo_telefone));
        //Fim das máscaras de InputView /**/



        getSharedPreferences("user_preferences", MODE_PRIVATE);

        usuarioCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }


    public void check(){
        View contextView = findViewById(R.id.butt_registra_usuario);

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
                        if(usuarioEndereco.getText().length()>=3){
                            if(usuarioTelefone.getText().length()>=8){
                                if(usuarioSenha.getText().length() >= 8){
                                    if(password.equals(confirmpassword)){

                                        sexo = ((RadioButton)findViewById(usuarioSexo.getCheckedRadioButtonId())).getText().toString();

                                        Toast.makeText(getApplicationContext(),"Todas as checagens foram feitas", Toast.LENGTH_SHORT).show();
                                        usuarioCadastra.setClickable(false);
                                        registra_usuario();

                                    }else{
                                        Snackbar.make(contextView,"As senhas não coincidem",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                        usuarioSenha.requestFocus();
                                    }
                                }else{
                                    Snackbar.make(contextView,"Insira uma senha",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                    usuarioSenha.requestFocus();
                                }
                            }else{
                                Snackbar.make(contextView,"Insira um telefone válido",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                usuarioTelefone.requestFocus();
                            }
                        }else{
                            Snackbar.make(contextView,"Insira um endereço válido",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                            usuarioEndereco.requestFocus();
                        }
                    }else{
                        Snackbar.make(contextView,"CPF inválido",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        usuarioCPF.requestFocus();
                    }
                }else{
                    Snackbar.make(contextView,"Insira sua data de nascimento",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    usuarioNascimento.requestFocus();
                }
            }else{
                Snackbar.make(contextView,"Insira um email",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                usuarioEmail.requestFocus();
            }
        }else{
            Snackbar.make(contextView,"Insira um nome",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            usuarioNome.requestFocus();
        }


    }

    private void registra_usuario() {
        progressDialog = ProgressDialog.show(CadastroUsuarioNovo.this, "Enviando dados", "Por favor aguarde", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_usuario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("responseString","response:"+response);
                        try {
                            //convertendo a resposta em um objeto json
                            JSONObject obj = new JSONObject(response);

                            //se não houver nenhum erro de resposta

                            // Toast.makeText(getApplicationContext(), "Sem erro de reposta", Toast.LENGTH_SHORT).show();

                            usuario_rede = 0;

                            //criando um objeto novo usuário
                            Usuario user = new Usuario(
                                    obj.getString("usuario_id"),
                                    obj.getString("usuario_nome"),
                                    obj.getString("usuario_email"),
                                    usuario_rede
                            );

                            //armazenando o usuário na sharedpreferences
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            progressDialog.dismiss();
                            //iniciando a activity de perfil
                            finish();

                            Log.i("UserID","id:"+obj.getInt("usuario_id"));

                            //Toast.makeText(getApplicationContext(),"id:"+ obj.getInt("usuario_id"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CadastroUsuarioFoto.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Erro de resposta","Erro:" +error);
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(), "erro de resposta 2" + error, Toast.LENGTH_SHORT).show();
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
                params.put("telefone",usuarioTelefone.getText().toString());
                params.put("endereco",usuarioEndereco.getText().toString());
                params.put("usuario_rede", String.valueOf(usuario_id));
                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

}



