package com.example.hoanglong.letstakeaflagquiz;

import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.widget.Toast;

public class activity_test extends Activity {
    Button ans_button1, ans_button2, ans_button3, ans_button4, help_button;
    ImageView quiz_image, ans_img1, ans_img2, ans_img3, ans_img4, ans_dec1, ans_dec2, ans_dec3, ans_dec4;
    TextView quiz_text, result, recentquiz,timer_text;
    List<Flag> list;

    SoundPool soundPool;


    private static int TIME_OUT = 2000; //Delay activity
    Random r;

    LinearLayout layout;

    MediaPlayer soundID_background;

    int tap = 0;
    int turn = 1;
    int score = 0;
    int easyHighScore = 0, normalHighScore = 0, hardHighScore = 0, mixHighScore = 0;
    int type;
    int help_int = 0;
    int help_count;
    CountDownTimer timer;

    int soundID_correct;
    int soundID_wrong;
    int soundID_pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test);

        Bundle bundle = getIntent().getExtras();
        int Mode = bundle.getInt("Mode");

        type = Mode;

        r = new Random();

        help_button = (Button) findViewById(R.id.Btn_fifty_fifty_help);

        layout = (LinearLayout) findViewById(R.id.LLayout);

        result = (TextView) findViewById(R.id.TxtResult);
        recentquiz = (TextView) findViewById(R.id.TxtRecentQuiz);

        timer_text = (TextView) findViewById(R.id.Txttimer);

        quiz_image = (ImageView) findViewById(R.id.Img_Quiz);
        quiz_text = (TextView) findViewById(R.id.Txt_Quiz);

        ans_button1 = (Button) findViewById(R.id.AnswerA_Btn);
        ans_button2 = (Button) findViewById(R.id.AnswerB_Btn);
        ans_button3 = (Button) findViewById(R.id.AnswerC_Btn);
        ans_button4 = (Button) findViewById(R.id.AnswerD_Btn);

        ans_img1 = (ImageView) findViewById(R.id.AnswerA_Img);
        ans_img2 = (ImageView) findViewById(R.id.AnswerB_Img);
        ans_img3 = (ImageView) findViewById(R.id.AnswerC_Img);
        ans_img4 = (ImageView) findViewById(R.id.AnswerD_Img);

        ans_dec1 = (ImageView) findViewById(R.id.AnswerA_Dec);
        ans_dec2 = (ImageView) findViewById(R.id.AnswerB_Dec);
        ans_dec3 = (ImageView) findViewById(R.id.AnswerC_Dec);
        ans_dec4 = (ImageView) findViewById(R.id.AnswerD_Dec);

        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "fonts/biggerlove.ttf");
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/scribble_box.ttf");

        timer_text.setTypeface(myCustomFont2);

        quiz_text.setTypeface(myCustomFont1);
        ans_button1.setTypeface(myCustomFont1);
        ans_button2.setTypeface(myCustomFont1);
        ans_button3.setTypeface(myCustomFont1);
        ans_button4.setTypeface(myCustomFont1);

        timer_text.setText("" + timer);
        loadHighScore();

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

        soundID_correct = soundPool.load(this, R.raw.correct_sound, 1);
        soundID_wrong = soundPool.load(this, R.raw.wrong_sound, 1);
        soundID_pop = soundPool.load(this, R.raw.pop_sound, 1);

        switch (Mode) {
            case 1:
                layout.setBackgroundResource(R.drawable.easy_star_background);
                makeList(0, 55);
                soundID_background = MediaPlayer.create(activity_test.this, R.raw.background_easy);
                soundID_background.start();
                doTest();
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.normal_star_background);
                ContextCompat.getDrawable(this, R.drawable.normal_star_background);
                makeList(56, 124);
                soundID_background = MediaPlayer.create(activity_test.this, R.raw.background_normal);
                soundID_background.start();
                doTest();
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.hard_star_background);
                makeList(125, new Database().answers.length);
                soundID_background = MediaPlayer.create(activity_test.this, R.raw.background_hard);
                soundID_background.start();
                doTest();
                break;
            case 4:
                layout.setBackgroundResource(R.drawable.mix_star_background);
                makeList(0, new Database().answers.length);
                soundID_background = MediaPlayer.create(activity_test.this, R.raw.background_mix);
                soundID_background.start();
                doTest();
                break;
        }
        help_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(soundID_pop,1,1,0,0,1);
                List<Integer> numbers = new ArrayList<Integer>() {{
                    add(1);
                    add(2);
                    add(3);
                    add(4);
                }};
                Collections.shuffle(numbers);
                help_count = 0;
                do {
                    for (int i = 0; i <= 2; i++) {
                        help_int = numbers.get(i);
                        if (help_int == 1) {
                            if (!(ans_button1.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName()))) {
                                ans_button1.setVisibility(View.GONE);
                                ans_img1.setVisibility(View.GONE);
                                ans_dec1.setVisibility(View.GONE);
                                help_count++;
                            }
                        } else if (help_int == 2) {
                            if (!(ans_button2.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName()))) {
                                ans_button2.setVisibility(View.GONE);
                                ans_img2.setVisibility(View.GONE);
                                ans_dec2.setVisibility(View.GONE);
                                help_count++;
                            }
                        } else if (help_int == 3) {
                            if (!(ans_button3.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName()))) {
                                ans_button3.setVisibility(View.GONE);
                                ans_img3.setVisibility(View.GONE);
                                ans_dec3.setVisibility(View.GONE);
                                help_count++;
                            }
                        } else if (help_int == 4) {
                            if (!(ans_button4.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName()))) {
                                ans_button4.setVisibility(View.GONE);
                                ans_img4.setVisibility(View.GONE);
                                ans_dec4.setVisibility(View.GONE);
                                help_count++;
                            }
                        } else help_int++;
                    }

                } while (help_count != 2);
                help_button.setEnabled(false);
            }
        });
    }

    void loadHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("My Data", Context.MODE_PRIVATE);
        if (sharedPreferences != null){
            easyHighScore = sharedPreferences.getInt("E",0);
            normalHighScore= sharedPreferences.getInt("N",0);
            hardHighScore = sharedPreferences.getInt("H",0);
            mixHighScore = sharedPreferences.getInt("M",0);
        }
    }

    protected void makeList(int start, int end) {
        list = new ArrayList<>();

        for (int i = start; i < end; i++) {
            list.add(new Flag(new Database().answers[i], new Database().flags[i]));
        }
        Collections.shuffle(list);
    }

    private void doTest() {
        int randomType = r.nextInt(2);
        switch (randomType) {
            case 0:

                ans_button1.setClickable(true);
                ans_button2.setClickable(true);
                ans_button3.setClickable(true);
                ans_button4.setClickable(true);
                help_button.setClickable(true);

                quiz_text.setAlpha(0);
                quiz_image.setVisibility(View.VISIBLE);

                ans_dec1.setBackgroundResource(R.drawable.blue_border);
                ans_dec2.setBackgroundResource(R.drawable.blue_border);
                ans_dec3.setBackgroundResource(R.drawable.blue_border);
                ans_dec4.setBackgroundResource(R.drawable.blue_border);

                ans_dec1.setVisibility(View.VISIBLE);
                ans_dec2.setVisibility(View.VISIBLE);
                ans_dec3.setVisibility(View.VISIBLE);
                ans_dec4.setVisibility(View.VISIBLE);

                ans_button1.setVisibility(View.VISIBLE);
                ans_button2.setVisibility(View.VISIBLE);
                ans_button3.setVisibility(View.VISIBLE);
                ans_button4.setVisibility(View.VISIBLE);

                ans_button1.setAlpha(1);
                ans_button2.setAlpha(1);
                ans_button3.setAlpha(1);
                ans_button4.setAlpha(1);

                ans_img1.setVisibility(View.INVISIBLE);
                ans_img2.setVisibility(View.INVISIBLE);
                ans_img3.setVisibility(View.INVISIBLE);
                ans_img4.setVisibility(View.INVISIBLE);
                showQuiz();
                break;
            case 1:

                ans_button1.setClickable(true);
                ans_button2.setClickable(true);
                ans_button3.setClickable(true);
                ans_button4.setClickable(true);
                help_button.setClickable(true);

                quiz_text.setAlpha(1);
                quiz_image.setVisibility(View.INVISIBLE);

                ans_dec1.setBackgroundResource(R.drawable.blue_border);
                ans_dec2.setBackgroundResource(R.drawable.blue_border);
                ans_dec3.setBackgroundResource(R.drawable.blue_border);
                ans_dec4.setBackgroundResource(R.drawable.blue_border);

                ans_dec1.setVisibility(View.VISIBLE);
                ans_dec2.setVisibility(View.VISIBLE);
                ans_dec3.setVisibility(View.VISIBLE);
                ans_dec4.setVisibility(View.VISIBLE);

                ans_button1.setAlpha(0);
                ans_button2.setAlpha(0);
                ans_button3.setAlpha(0);
                ans_button4.setAlpha(0);

                ans_img1.setVisibility(View.VISIBLE);
                ans_img2.setVisibility(View.VISIBLE);
                ans_img3.setVisibility(View.VISIBLE);
                ans_img4.setVisibility(View.VISIBLE);
                showQuiz();
                break;
        }
    }

    private void showQuiz() {
        newQuiz(turn);
        startTimer();
        chooseAnswer(ans_button1);
        chooseAnswer(ans_button2);
        chooseAnswer(ans_button3);
        chooseAnswer(ans_button4);
        result.setText(""+ score + "/20");
        recentquiz.setText("" + turn);
    }

    private void newQuiz(int number) {
        quiz_text.setText(list.get(number - 1).getName());
        quiz_image.setImageResource(list.get(number - 1).getImage());

        int correct_answer = r.nextInt(4) + 1;

        switch (correct_answer) {
            case 1:
                mixAnswer(ans_button1, number, ans_button2, ans_button3, ans_button4);
                mixPicture(ans_img1, number, ans_img2, ans_img3, ans_img4);
                break;
            case 2:
                mixAnswer(ans_button2, number, ans_button1, ans_button3, ans_button4);
                mixPicture(ans_img2, number, ans_img1, ans_img3, ans_img4);
                break;
            case 3:
                mixAnswer(ans_button3, number, ans_button1, ans_button2, ans_button4);
                mixPicture(ans_img3, number, ans_img2, ans_img1, ans_img4);
                break;
            case 4:
                mixAnswer(ans_button4, number, ans_button1, ans_button2, ans_button3);
                mixPicture(ans_img4, number, ans_img2, ans_img3, ans_img1);
                break;
        }
    }

    private void mixAnswer(Button b1, int number, Button b2, Button b3, Button b4) {

        int firstbutton = number - 1;
        int secondbutton;
        int thirdbutton;
        int fourthbutton;

        b1.setText(list.get(firstbutton).getName());

        do {
            secondbutton = r.nextInt(list.size());
        } while (secondbutton == firstbutton);
        do {
            thirdbutton = r.nextInt(list.size());
        } while (thirdbutton == firstbutton || thirdbutton == secondbutton);
        do {
            fourthbutton = r.nextInt(list.size());
        }
        while (fourthbutton == firstbutton || fourthbutton == secondbutton || fourthbutton == thirdbutton);

        b2.setText(list.get(secondbutton).getName());
        b3.setText(list.get(thirdbutton).getName());
        b4.setText(list.get(fourthbutton).getName());
    }

    private void mixPicture(ImageView i1, int number, ImageView i2, ImageView i3, ImageView i4) {
        int firstimage = number - 1;
        int secondimage;
        int thirdimage;
        int fourthimage;

        i1.setImageResource(list.get(firstimage).getImage());

        do {
            secondimage = r.nextInt(list.size());
        } while (secondimage == firstimage);
        do {
            thirdimage = r.nextInt(list.size());
        } while (thirdimage == firstimage || thirdimage == secondimage);
        do {
            fourthimage = r.nextInt(list.size());
        }
        while (fourthimage == firstimage || fourthimage == secondimage || fourthimage == thirdimage);

        i2.setImageResource(list.get(secondimage).getImage());
        i3.setImageResource(list.get(thirdimage).getImage());
        i4.setImageResource(list.get(fourthimage).getImage());
    }

    private void startTimer() {
        timer = new CountDownTimer(10 * 1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_text.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timer.cancel();
                AlertDialog alertDialog = new AlertDialog.Builder(activity_test.this).create();
                alertDialog.setTitle("!!! TIMED OUT !!!");
                if (turn <20) {
                    alertDialog.setMessage("The next quiz will be shown.");
                }
                else {
                    alertDialog.setMessage("GAME OVER! That is the final quiz!");
                }
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                endGame();
                            }
                        });
                alertDialog.show();
            }
        };
        timer.start();
    }

    protected void chooseAnswer(final Button b) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tap++;
                switch (v.getId()){
                    case R.id.AnswerA_Btn:
                        ans_dec1.setBackgroundResource(R.drawable.blue_border_select_ans);
                        break;
                    case R.id.AnswerB_Btn:
                        ans_dec2.setBackgroundResource(R.drawable.blue_border_select_ans);
                        break;
                    case R.id.AnswerC_Btn:
                        ans_dec3.setBackgroundResource(R.drawable.blue_border_select_ans);
                        break;
                    case R.id.AnswerD_Btn:
                        ans_dec4.setBackgroundResource(R.drawable.blue_border_select_ans);
                        break;
                }
                Handler handler = new Handler();
                Runnable runn = new Runnable() {
                    @Override
                    public void run() {
                        tap = 0;
                    }
                };
                if (tap == 1) {
                    handler.postDelayed(runn, 500);
                    if (b.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())) {
                        Toast.makeText(activity_test.this, "Correct!", Toast.LENGTH_SHORT).show();
                        soundPool.play(soundID_correct,1,1,0,0,1);
                        rightAnswer();
                        score++;
                        timer.cancel();
                        ans_button1.setClickable(false);
                        ans_button2.setClickable(false);
                        ans_button3.setClickable(false);
                        ans_button4.setClickable(false);
                        help_button.setClickable(false);
                        endGame();
                    } else {
                        Toast.makeText(activity_test.this, "Wrong!", Toast.LENGTH_SHORT).show();
                        soundPool.play(soundID_wrong,1,1,0,0,1);
                        rightAnswer();
                        timer.cancel();
                        ans_button1.setClickable(false);
                        ans_button2.setClickable(false);
                        ans_button3.setClickable(false);
                        ans_button4.setClickable(false);
                        help_button.setClickable(false);
                        endGame();
                    }
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity_test.this).create();
                    alertDialog.setTitle("!!! WARNING !!!");
                    alertDialog.setMessage("Slow down... Don't tap so quick!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }

    private void rightAnswer() {
        if (ans_button1.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())) {
            ans_dec1.setBackgroundResource(R.drawable.blue_border_right_ans);
        }
        else if (ans_button2.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())) {
            ans_dec2.setBackgroundResource(R.drawable.blue_border_right_ans);
        }
        else if (ans_button3.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName())) {
            ans_dec3.setBackgroundResource(R.drawable.blue_border_right_ans);
        }
        else {
            ans_dec4.setBackgroundResource(R.drawable.blue_border_right_ans);
        }
    }

    protected void endGame() {
        if (turn < 20) {
            turn++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doTest();
                }
            }, TIME_OUT);
        } else {
            Toast.makeText(activity_test.this, "You've finished the game!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity_test.this, activity_result.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Result", score);
            bundle.putInt("Quiz", turn);
            intent.putExtra("My Package", bundle);
            soundID_background.stop();
            soundID_background.release();
            startActivity(intent);
            switch (type) {
                case 1:
                    if (score > easyHighScore)
                    {
                        easyHighScore = score;
                    }
                    break;
                case 2:
                    if (score > normalHighScore)
                    {
                        normalHighScore = score;
                    }
                    break;
                case 3:
                    if (score > hardHighScore)
                    {
                        hardHighScore = score;
                    }
                    break;
                case 4:
                    if (score > mixHighScore)
                    {
                        mixHighScore = score;
                    }
                    break;
            }
            saveHighScore();
            finish();
        }
    }

    void saveHighScore(){
        SharedPreferences sharedPreferences = getSharedPreferences("My Data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("E", easyHighScore);
        editor.putInt("N", normalHighScore);
        editor.putInt("H", hardHighScore);
        editor.putInt("M", mixHighScore);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        onPause();
        AlertDialog.Builder alert = new AlertDialog.Builder(activity_test.this);
        alert.setMessage("Do you want to quit the quiz?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        turn = 20;
                        endGame();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        onResume();
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.setTitle("You have just pressed Back Button");
        onPause();
        dialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}