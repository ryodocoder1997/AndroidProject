package com.example.hoanglong.letstakeaflagquiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.TextView;

public class activity_guide extends Activity {
    ImageView guide, endguide;
    Button next, back;
    TextView steps;

    SoundPool soundPool;

    MediaPlayer soundguide;

    int count = 1;
    int soundID_pop;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        guide = (ImageView) findViewById(R.id.Img_Guide);
        endguide = (ImageView)findViewById(R.id.Img_GuideContinued);

        next = (Button) findViewById(R.id.Btn_NextArrow);
        back = (Button) findViewById(R.id.Btn_BackArrow);

        steps = (TextView) findViewById(R.id.Txt_Guide);

        soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide1_sound);


        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/scribble_box.ttf");
        steps.setTypeface(myCustomFont);

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

        steps.setText("" + count + "/10");

        soundguide.start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundguide.stop();
                soundguide.release();
                soundPool.play(soundID_pop,1,1,0,0,1);
                count++;
                showGuide();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 1) {
                    soundguide.stop();
                    soundguide.release();
                    soundPool.play(soundID_pop,1,1,0,0,1);
                    Intent intent = new Intent(activity_guide.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    soundguide.stop();
                    soundguide.release();
                    soundPool.play(soundID_pop,1,1,0,0,1);
                    count--;
                    showGuide();
                }
            }
        });

        endguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_guide.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void showGuide() {
        switch (count) {
            case 1:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide1_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page1);
                steps.setText("" + count + "/10");
                break;
            case 2:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide2_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page2);
                steps.setText("" + count + "/10");
                break;
            case 3:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide3_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page3);
                steps.setText("" + count + "/10");
                break;
            case 4:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide4_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page4);
                steps.setText("" + count + "/10");
                break;
            case 5:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide5_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page5);
                steps.setText("" + count + "/10");
                break;
            case 6:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide6_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page6);
                steps.setText("" + count + "/10");
                break;
            case 7:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide7_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page7);
                steps.setText("" + count + "/10");
                break;
            case 8:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide8_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page8);
                steps.setText("" + count + "/10");
                break;
            case 9:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide9_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page9);
                steps.setText("" + count + "/10");
                break;
            case 10:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide10_sound);
                soundguide.start();
                guide.setBackgroundResource(R.drawable.guide_page10);
                steps.setText("" + count + "/10");
                break;
            case 11:
                soundguide = MediaPlayer.create(activity_guide.this, R.raw.guide11_sound);
                soundguide.start();
                steps.setText("");
                endguide.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(activity_guide.this, MainActivity.class);
        startActivity(back);
    }
}
