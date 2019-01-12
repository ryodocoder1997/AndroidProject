package com.example.hoanglong.letstakeaflagquiz;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.app.Activity;


public class activity_chooseQuizDifficulty extends Activity {

    RadioButton RdbEasy, RdbNormal, RdbHard, RdbMix;
    Button BtnStart;
    RadioGroup choice;

    TextView t_ask;

    SoundPool soundPool;

    int soundID_pop;
    int soundID_start;

    public int difficultyMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_difficulty);

        t_ask = (TextView) findViewById(R.id.TxtAsk);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/comic_sans_ms_regular.ttf");
        t_ask.setTypeface(myCustomFont);

        choice = (RadioGroup) findViewById(R.id.RadioGroupDSelect);

        RdbEasy = (RadioButton) findViewById(R.id.RdbEasy);
        RdbNormal = (RadioButton) findViewById(R.id.RdbNormal);
        RdbHard = (RadioButton) findViewById(R.id.RdbHard);
        RdbMix = (RadioButton) findViewById(R.id.RdbMix);

        BtnStart = (Button) findViewById(R.id.BtnStart);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        soundID_pop = soundPool.load(this, R.raw.pop_sound,1);
        soundID_start = soundPool.load(this, R.raw.start_sound, 1);

        soundPool.play(soundID_pop,1,1,0,0,1);

        choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RdbEasy:
                        soundPool.play(soundID_pop,1,1,0,0,1);
                    case R.id.RdbNormal:
                        soundPool.play(soundID_pop,1,1,0,0,1);
                    case R.id.RdbHard:
                        soundPool.play(soundID_pop,1,1,0,0,1);
                    case R.id.RdbMix:
                        soundPool.play(soundID_pop,1,1,0,0,1);
                }
            }
        });

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID_start,1,1,0,0,1);
                int idCheck = choice.getCheckedRadioButtonId();
                switch (idCheck) {
                    case R.id.RdbEasy:
                        difficultyMode = 1;
                        Intent easy = new Intent(activity_chooseQuizDifficulty.this, activity_test.class);
                        easy.putExtra("Mode",difficultyMode);
                        startActivity(easy);
                        break;
                    case R.id.RdbNormal:
                        difficultyMode = 2;
                        Intent normal = new Intent(activity_chooseQuizDifficulty.this, activity_test.class);
                        normal.putExtra("Mode", difficultyMode);
                        startActivity(normal);
                        break;
                    case R.id.RdbHard:
                        difficultyMode = 3;
                        Intent hard = new Intent(activity_chooseQuizDifficulty.this, activity_test.class);
                        hard.putExtra("Mode", difficultyMode);
                        startActivity(hard);
                        break;
                    case R.id.RdbMix:
                        difficultyMode = 4;
                        Intent mix = new Intent(activity_chooseQuizDifficulty.this, activity_test.class);
                        mix.putExtra("Mode", difficultyMode);
                        startActivity(mix);
                        break;
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(activity_chooseQuizDifficulty.this, MainActivity.class);
        startActivity(back);
    }
}
