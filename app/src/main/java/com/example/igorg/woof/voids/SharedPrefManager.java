package com.example.igorg.woof.voids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.igorg.woof.LoginActivity;
import com.example.igorg.woof.modelo.Estabelecimento;
import com.example.igorg.woof.modelo.Usuario;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_ESTABNAME = "keyestabname";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_MEDIA = "keymedia";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IDESTAB = "keyestabid";
    private static final String EKEY_NAME = "keyname";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Usuario usuario) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, usuario.getId());
        editor.putString(KEY_USERNAME, usuario.getNome());
        editor.putString(KEY_EMAIL, usuario.getEmail());
        editor.putInt(KEY_MEDIA, usuario.getMedia());
        editor.apply();
    }

    public void userEstabelecimento(Estabelecimento estabelecimento) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IDESTAB, estabelecimento.getEstabelecimento_id());
        editor.putString(KEY_ESTABNAME, estabelecimento.getEstabelecimento_nome());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public Usuario getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Usuario(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getInt(KEY_MEDIA,0)
        );
    }

    public Estabelecimento getEstabelecimento() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Estabelecimento(
                sharedPreferences.getString(KEY_IDESTAB, null),
                sharedPreferences.getString(KEY_ESTABNAME, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}