package com.example.hoanglong.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView img_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        img_welcome = (ImageView) findViewById(R.id.img_welcome);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, SecondSplashScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        },1500);

    }
}
