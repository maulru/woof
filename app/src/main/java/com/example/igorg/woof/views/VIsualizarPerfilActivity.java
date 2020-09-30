package com.example.igorg.woof.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.masks.Mask;
import com.example.igorg.woof.modelo.Pet;
import com.example.igorg.woof.modelo.Servidor;
import com.example.igorg.woof.modelo.Usuario;
import com.example.igorg.woof.voids.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.usuario_rede;

public class VIsualizarPerfilActivity extends AppCompatActivity {

    private EditText cpf,telefone,endereco,email,senha,nascimento;
    private TextView nome,cidade,pets,agendamentos;
    private ImageView editar;
    private Button salvar;
    private static final int MY_REQUEST_INT = 10;

    private String servidor, url;

    JsonArrayRequest jsonArrayRequest;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ProgressDialog progressDialog;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil2);

        cpf = (EditText) findViewById(R.id.edit_perfil_cpf);
        telefone = (EditText) findViewById(R.id.edit_perfil_telefone);
        endereco = (EditText) findViewById(R.id.edit_perfil_endereco);
        email = (EditText) findViewById(R.id.edit_perfil_email);
        senha = (EditText) findViewById(R.id.edit_perfil_senha);
        nascimento = (EditText) findViewById(R.id.edit_perfil_nascimento);

        nome = (TextView) findViewById(R.id.text_perfil_nome);
        pets = (TextView) findViewById(R.id.text_perfil_pets);
        editar = (ImageView) findViewById(R.id.butt_perfil_editar);

        salvar = (Button) findViewById(R.id.butt_perfil_salvar);

        usuario = SharedPrefManager.getInstance(this).getUser();

        if(usuario.getMedia() == 0){
            email.setVisibility(View.VISIBLE);
            senha.setVisibility(View.VISIBLE);
        }else{
            email.setVisibility(View.INVISIBLE);
            senha.setVisibility(View.INVISIBLE);
        }

        cpf.setEnabled(false);
        telefone.setEnabled(false);
        endereco.setEnabled(false);
        email.setEnabled(false);
        senha.setEnabled(false);
        nascimento.setEnabled(false);

        salvar.setVisibility(View.INVISIBLE);

        servidor = Servidor.getIp();



        url = "http://"+servidor+"/woof/busca_perfil_usuario.php?h="+usuario.getId()+"&usuario_rede="+usuario.getMedia();

        JSON_WEB_CALL();

        //Início das máscaras de InputView
        final EditText campo_data_nascimento = (EditText) findViewById(R.id.edit_perfil_nascimento);
        campo_data_nascimento.addTextChangedListener(Mask.insert("##/##/####", campo_data_nascimento));

        final EditText campo_cpf = (EditText) findViewById(R.id.edit_perfil_cpf);
        campo_cpf.addTextChangedListener(Mask.insert("###.###.###-##", campo_cpf));

        final EditText campo_telefone = (EditText) findViewById(R.id.edit_perfil_telefone);
        campo_telefone.addTextChangedListener(Mask.insert("(##)#####-####", campo_telefone));
        //Fim das máscaras de InputView /**/


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cpf.setEnabled(true);
                telefone.setEnabled(true);
                endereco.setEnabled(true);


                nascimento.setEnabled(true);

                if(usuario.getMedia() == 0){
                    senha.setEnabled(true);
                    email.setEnabled(true);
                }else{
                    senha.setEnabled(false);
                    email.setEnabled(false);

                }

                salvar.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Edite as informações do seu perfil!", Toast.LENGTH_LONG).show();

            }
        });


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizar_perfil();
            }
        });


    }


    public void JSON_WEB_CALL(){

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Sucesso","JSONObject: "+response);

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ResponseError","Erro: "+error);
            }
        });



        requestQueue = Volley.newRequestQueue(VIsualizarPerfilActivity.this);

        requestQueue.add(jsonObjectRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONObject object) {

        int id = 0;
        String nome_json,cpf_json,nascimento_json,email_json,telefone_json,endereco_json,cep_json,complemento_json,senha_json,animais;


        JSONObject json = object;
        try {


            id = json.getInt("ID_Usuario");
            nome_json = json.getString("Nome");
            cpf_json = json.getString("Cpf");
            nascimento_json = json.getString("Dt_Nascimento");
            email_json = json.getString("Email");
            telefone_json = json.getString("Telefone");
            endereco_json = json.getString("Endereco");
            cep_json = json.getString("Cep");
            complemento_json = json.getString("Complemento");
            senha_json = json.getString("Senha");
            animais = json.getString("Animais");


            nome.setText(nome_json);
            cpf.setText(cpf_json);
            nascimento.setText(nascimento_json);
            email.setText(email_json);
            telefone.setText(telefone_json);
            endereco.setText(endereco_json);
            senha.setText(senha_json);
            pets.setText(animais);




        } catch (JSONException e) {

            e.printStackTrace();
        }






    }

    private void atualizar_perfil() {

        progressDialog = ProgressDialog.show(VIsualizarPerfilActivity.this, "Enviando dados", "Por favor aguarde", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+servidor+"/woof/atualizar_usuario.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("responseString","response:"+response);
                        try {
                            //convertendo a resposta em um objeto json
                            JSONObject obj = new JSONObject(response);

                            //se não houver nenhum erro de resposta

                            // Toast.makeText(getApplicationContext(), "Sem erro de reposta", Toast.LENGTH_SHORT).show();


                            progressDialog.dismiss();
                            //iniciando a activity de perfil
                            finish();

                            Log.i("UserID","id:"+obj.getInt("usuario_id"));

                            //Toast.makeText(getApplicationContext(),"id:"+ obj.getInt("usuario_id"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
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
                params.put("nome",nome.getText().toString());
                params.put("email",email.getText().toString());
                params.put("cpf",cpf.getText().toString());
                params.put("data_nascimento", nascimento.getText().toString());
                params.put("senha",senha.getText().toString());
                params.put("telefone",telefone.getText().toString());
                params.put("endereco",endereco.getText().toString());
                params.put("h",usuario.getId());
                params.put("usuario_rede", String.valueOf(usuario.getMedia()));
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
