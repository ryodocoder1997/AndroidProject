package com.example.hoanglong.letstakeaflagquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class activity_highScore extends Activity {
    TextView TxtEasyHighscore, TxtNormalHighscore, TxtHardHighscore,TxtMixHighscore;
    Button BtnBack;

    SoundPool soundPool;

    int soundID_tap;

    int easyHighScore, normalHighScore, hardHighScore, mixHighScore;
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_highscores);

        LoadHighScore();

        TxtEasyHighscore = (TextView)findViewById(R.id.TxtEasyHighscore);
        TxtEasyHighscore.setText("" + easyHighScore);

        TxtNormalHighscore = (TextView)findViewById(R.id.TxtNormalHighscore);
        TxtNormalHighscore.setText(" " + normalHighScore);

        TxtHardHighscore = (TextView)findViewById(R.id.TxtHardHighscore);
        TxtHardHighscore.setText(" "+hardHighScore);

        TxtMixHighscore = (TextView)findViewById(R.id.TxtMixHighscore);
        TxtMixHighscore.setText(" " + mixHighScore);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(3)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }

        soundID_tap = soundPool.load(this, R.raw.tap_sound, 1);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/scribble_box.ttf");
        TxtEasyHighscore.setTypeface(myCustomFont);
        TxtNormalHighscore.setTypeface(myCustomFont);
        TxtHardHighscore.setTypeface(myCustomFont);
        TxtMixHighscore.setTypeface(myCustomFont);

        BtnBack = (Button)findViewById(R.id.BtnBack);
        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID_tap,1,1,0,0,1);
                Intent i = new Intent(activity_highScore.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    void LoadHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("My Data", Context.MODE_PRIVATE);
        if (sharedPreferences != null){
            easyHighScore = sharedPreferences.getInt("E",0);
            normalHighScore= sharedPreferences.getInt("N",0);
            hardHighScore = sharedPreferences.getInt("H",0);
            mixHighScore = sharedPreferences.getInt("M",0);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(activity_highScore.this, MainActivity.class);
        startActivity(back);
    }
}