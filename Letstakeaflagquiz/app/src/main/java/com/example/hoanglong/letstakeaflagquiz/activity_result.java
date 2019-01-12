package com.example.hoanglong.letstakeaflagquiz;

import android.content.Intent;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class activity_result extends Activity {
    TextView RS;
    Button BT;
    SoundPool soundPool;

    int soundID_tap;

    MediaPlayer soundID_applause;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        RS = (TextView) findViewById(R.id.TxtKQ);
        BT = (Button) findViewById(R.id.BtnBacktoScreen);

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

        soundID_applause = MediaPlayer.create(activity_result.this, R.raw.applause_sound);
        soundID_applause.start();

        Intent callerIntent = getIntent();
        Bundle packageFromCaller = callerIntent.getBundleExtra("My Package");

        RS.setText(packageFromCaller.getInt("Result") + "/" + packageFromCaller.getInt("Quiz"));
        BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID_tap,1,1,0,0,1);
                Intent i = new Intent(activity_result.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        soundID_applause.stop();
        soundID_applause.release();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
