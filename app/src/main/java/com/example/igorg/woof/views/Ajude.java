package com.example.igorg.woof.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.igorg.peteazyv20.R;

import java.util.Locale;

public class Ajude extends AppCompatActivity {
    Button mapa,ong;

    private TextToSpeech mTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajude);

        mapa = (Button) findViewById(R.id.btnMapa);
        mapa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Ajude.this, MapsAnimal.class);
                startActivity(intent);


            }
        });



    ong = (Button) findViewById(R.id.btnOng);
        ong.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(Ajude.this, ListagemONGActivity.class);
            startActivity(intent);



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
    public void falar() {
        mTTS.speak("Ajude os Animais", TextToSpeech.QUEUE_FLUSH, null);
    }




}
