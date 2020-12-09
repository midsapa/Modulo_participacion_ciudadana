package edu.continental.modparticipacionciudadana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {
    ImageView logo;
    TextView logoTexto, logoTexto1;
    Animation animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo=findViewById(R.id.logo);
        logoTexto1=findViewById(R.id.textoLogo1);
        logoTexto=findViewById(R.id.textoLogo);
        animacion= AnimationUtils.loadAnimation(this,R.anim.animation);
        logo.startAnimation(animacion);
        logoTexto.startAnimation(animacion);
        logoTexto1.startAnimation(animacion);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(splash.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        },3000);
    }
}