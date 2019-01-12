package com.example.hoanglong.letstakeaflagquiz;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;

import static android.media.MediaPlayer.create;

public class MainActivity extends AppCompatActivity {

    Button Start, HighScores, Guide, Quit;

    SoundPool soundPool;
    AudioAttributes attributes;

    private  static int SPLASH_TIMED_OUT = 5000;
    int TIME_DELAYED = 1000;
    int soundID_start;


    MediaPlayer soundID_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

            Start = (Button) findViewById(R.id.BtnTest);
            HighScores = (Button) findViewById(R.id.BtnHighScore);
            Guide = (Button) findViewById(R.id.BtnGuide);
            Quit = (Button) findViewById(R.id.BtnQuit);

            soundID_background = MediaPlayer.create(MainActivity.this, R.raw.background_sound);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();
                soundPool = new SoundPool.Builder()
                        .setMaxStreams(1)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else {
                soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            }

            soundID_start = soundPool.load(this, R.raw.start_sound, 1);


            soundID_background.start();

            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    soundPool.play(soundID_start, 1, 1, 0, 0, 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, activity_chooseQuizDifficulty.class);
                            startActivity(intent);
                        }
                    }, TIME_DELAYED);
                }
            });

            HighScores.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    soundPool.play(soundID_start, 1, 1, 0, 0, 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, activity_highScore.class);
                            startActivity(intent);
                        }
                    }, TIME_DELAYED);
                }
            });

            Guide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    soundPool.play(soundID_start, 1, 1, 0, 0, 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, activity_guide.class);
                            startActivity(intent);
                        }
                    }, TIME_DELAYED);
                }
            });

            Quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    soundPool.play(soundID_start, 1, 1, 0, 0, 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            appExit();
                        }
                    }, TIME_DELAYED);

                }
            });
        }

    @Override
    protected void onPause() {
        super.onPause();
        soundID_background.stop();
        soundID_background.release();
    }

    @Override
    protected void onStart() {
        super.onStart();
        soundID_background.setLooping(true);
    }

    public void appExit() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}