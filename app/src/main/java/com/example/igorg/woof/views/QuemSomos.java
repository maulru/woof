package com.example.igorg.woof.views;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.igorg.peteazyv20.R;

import java.util.Locale;

public class QuemSomos extends AppCompatActivity {
    private TextToSpeech mTTS;
    TextView quem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quem_somos);

        quem = (TextView)findViewById(R.id.btn_quemsomos);

        final Animation myAnim = AnimationUtils.loadAnimation(QuemSomos.this, R.anim.bounce);
        quem.startAnimation(myAnim);

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


        mTTS.speak("Utilizer nossos serviços de organização, agendamento, busca de estabelecimento e aletas sobre os pets desabrigados e acidentados tornando a comunidade pet mais feliz", TextToSpeech.QUEUE_FLUSH, null);

    }



}



