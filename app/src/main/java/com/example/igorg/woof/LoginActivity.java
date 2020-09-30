package com.example.igorg.woof;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.igorg.woof.views.CadastroUsuarioActivity;
import com.example.igorg.woof.views.CadastroUsuarioNovo;
import com.example.igorg.woof.views.HomeUserActivity;
import com.example.igorg.woof.views.RecuperaSenhaActivity;
import com.example.igorg.woof.voids.SharedPrefManager;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.igorg.woof.modelo.Servidor.usuario_rede;

public class LoginActivity extends AppCompatActivity {
    ImageView facebook, gmail;
    EditText et_email, et_senha;
    Button cadastrar,logar;
    public String server, id_facebook, id_google,nome_facebook,nome_google,email_facebook,email_google;

    private TextView esqueceu;


    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    Usuario usuario;

    public static LoginButton loginButton;
    public static CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telaloginagoravai);

        id_facebook="";
        nome_facebook="";
        email_facebook="";

        id_google="";
        nome_google="";
        email_google="";

        usuario= SharedPrefManager.getInstance(this).getUser();

        esqueceu = (TextView) findViewById(R.id.text_esqueceu_senha);

        esqueceu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecuperaSenhaActivity.class));
            }
        });

        //Código para informar a Keyhash do app
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),  //Or replace to your package name directly, instead getPackageName()  "com.your.app"
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {

        }

        //Inicialização de componentes
        et_email = findViewById(R.id.cc);
        et_senha = findViewById(R.id.dd);
        logar = findViewById(R.id.gg);
        cadastrar = findViewById(R.id.cadastre);

        callbackManager = CallbackManager.Factory.create();

        //Selecionar IP do Servidor
        server = (String) Servidor.getIp();


        //Cadastro via email
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent(LoginActivity.this,
                        CadastroUsuarioNovo.class);
                startActivity(cadastro);
            }
        });

        //Login com email
        logar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String mEmail = et_email.getText().toString().trim();
                String mSenha = et_senha.getText().toString().trim();


                if (!mEmail.isEmpty() || !mSenha.isEmpty()) {
                    Login();
                } else {
                    et_email.setError("Por favor insira o email");
                    et_senha.setError("Por favor insira a senha");
                }
            }
        });

        //Código para login com Facebook
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
       // checkLoginStatus();

        checarLogin();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
             //   Toast.makeText(getApplicationContext(),  "Complete!!!", Toast.LENGTH_SHORT).show();
               Log.i("Complete","Complete!!!");
                executeGraphRequest(loginResult.getAccessToken().getUserId());

                usuario_rede = 1;

                Log.i("rede","ID:"+usuario_rede);
                Log.i("variaveis",nome_facebook + "," + id_facebook + "," + email_facebook);

                startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));
            }

            @Override
            public void onCancel() {
                Log.d("Erro","onCancel Error");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Erro","Facebook exception Error: " +error);
            }
        });

        //Código botão google

        //Initializing Views Google
        signInButton = findViewById(R.id.sign_in_button);

        // Configure sign-in to request the GOOGLE user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //login google
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }


    public void checarLogin(){

        if ((usuario.getId() != null) && (usuario.getMedia() == 0)){

            usuario_rede = 0;
            startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));

        } else if ((usuario.getId() != null) && (usuario.getMedia() == 1)){

            usuario_rede = 1;
            checkLoginStatus();

        } else if ((usuario.getId() != null) && (usuario.getMedia() == 2)){

            usuario_rede = 2;
            startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));

        }

    }






    //on result Google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.i("Google","task:"+task);
            handleSignInResult(task);
        }

    }

    //token facebook
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
            if(currentAccessToken==null)
            {
              //  Toast.makeText(LoginActivity.this,"token nulo", Toast.LENGTH_SHORT).show();

                Log.e("Token","nulo!!!");
            }
            else
                loadUserProfile(currentAccessToken);
        }
    };

    //carregar perfil facebook
    private void loadUserProfile(AccessToken newAccessToken)
    {

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    String name = first_name +" "+last_name;

                    usuario_rede = 1;

                    Usuario user = new Usuario(
                           id,
                           name,
                            email,
                            usuario_rede
                    );



                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                    //starting the profile activity
                    finish();
                    Log.d("UserID","ID:"+id);
                    startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    //check login facebook
    private void checkLoginStatus()
    {
        if(AccessToken.getCurrentAccessToken()!=null)
        {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }



    //login com email
    public void Login(){
        StringRequest request = new StringRequest(Request.Method.POST, "http://"+server+"/woof/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.contains("1")){

                            usuario_rede = 0;
                            Log.i("rede","ID:"+usuario_rede);
                            try {
                                //convertendo a resposta em um objeto json
                                JSONObject obj = new JSONObject(response);

                                //se não houver nenhum erro de resposta

                             //   Toast.makeText(getApplicationContext(), "Sem erro de reposta", Toast.LENGTH_SHORT).show();

                                Log.i("Sem erro de resposta","Sem erro!!!");

                                //criando um objeto novo usuário
                                Usuario user = new Usuario(
                                        obj.getString("ID_Usuario"),
                                        obj.getString("Nome"),
                                        obj.getString("Email"),
                                        usuario_rede = 0
                                );

                                //armazenando o usuário na sharedpreferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                Log.i("userid","userid:"+user.getId());
                                //iniciando a activity de perfil
                                finish();
                               Log.i("Response_login","Response:"+response);
                                startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                         //   Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                            Log.i("Erro","Erro:"+response);

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email ou senha errado",Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params =new HashMap<>();
                params.put("email", et_email.getText().toString());
                params.put("senha",et_senha.getText().toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    //login google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //login google 2
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.

            Log.i("Google","google:"+account);
            Log.i("Google","google:"+completedTask);

            if (account != null) {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();
                usuario_rede = 2;

                Usuario user = new Usuario(
                        personId,
                        personName,
                        personEmail,
                        usuario_rede
                );

                    registrarGoogle(personName,personId,personEmail);




                //storing the user in shared preferences
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                Log.i("Google","user:"+user.getMedia());

            }

            startActivity(new Intent(LoginActivity.this, HomeUserActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed =" + e.getStatusCode(), Toast.LENGTH_LONG).show();
        }
    }

    //coletar dados facebook
    private void executeGraphRequest(final String userId){
        GraphRequest request =new GraphRequest(AccessToken.getCurrentAccessToken(), userId, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Log.i("FACEBOOK", response.getJSONObject().toString());
                Log.i("FACEBOOK", Profile.getCurrentProfile().toString());

                JSONObject json = response.getJSONObject();

                try {
                    nome_facebook = json.getString("name");
                    id_facebook = json.getString("id");
                    email_facebook = json.getString("email");

                    String nome, id, email;

                    nome = nome_facebook;
                    id = id_facebook;
                    email = email_facebook;

                    Log.i("Variaveis","try = " + nome + "," + id +"," + email);

                    registrarFace(nome,id,email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email, gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }

    //inserir usuario no banco de dados com os dados do facebook
    public void registrarFace(final String nome, final String id, final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_facebook.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("responseString","response:"+response);

                        usuario_rede = 1;

                        Usuario user = new Usuario(
                                id,
                                nome,
                                email,
                                usuario_rede
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        Log.i("Facebook","rede_id:"+user.getMedia());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro de resposta 2", Toast.LENGTH_SHORT).show();
                        Log.i("Erro volley","erro:"+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_facebook",id);
                params.put("email",email);
                params.put("nome",nome);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    public void registrarGoogle(final String nome, final String id, final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+server+"/woof/registrar_google.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("responseString","response:"+response);

                        usuario_rede = 2;

                        Usuario user = new Usuario(
                                id,
                                nome,
                                email,
                                usuario_rede
                        );


                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        Log.i("Google","rede_id:"+user.getMedia()+" userid:"+user.getId());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro de resposta 2", Toast.LENGTH_SHORT).show();
                        Log.i("Erro volley","erro:"+error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_google",id);
                params.put("email",email);
                params.put("nome",nome);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


}