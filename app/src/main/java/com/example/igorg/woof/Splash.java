package com.example.igorg.woof;



import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.igorg.peteazyv20.R;
import com.example.igorg.woof.views.Apresentacao;

import java.util.Locale;

import maes.tech.intentanim.CustomIntent;


public class Splash extends AppCompatActivity {
    private TextToSpeech mTTS;
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


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



        logo = (ImageView) findViewById(R.id.image);
        Animation logoAnim = AnimationUtils.loadAnimation(this, R.anim.cima_pra_baixo);
        logo.startAnimation(logoAnim);

        Handler handler_home = new Handler();


        Handler handler_falar = new Handler();


        handler_falar.postDelayed(new Runnable() {
            @Override
            public void run() {

                speak();

            }
        }, 1000);



        handler_home.postDelayed(new Runnable() {
            @Override
            public void run() {

                mostrarHome();

            }
        }, 3000);
    }

    private void mostrarHome() {
        Intent intent = new Intent(
                Splash.this,Apresentacao.class
        );
        startActivity(intent);
        finish();
    }


/*

        logo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Animation myAnim = AnimationUtils.loadAnimation(Splash.this, R.anim.bounce);
                logo.startAnimation(myAnim);


            }
        });



*/


    public void speak () {


        mTTS.speak("Bem Vindo ao Woof", TextToSpeech.QUEUE_FLUSH, null);
    }

}