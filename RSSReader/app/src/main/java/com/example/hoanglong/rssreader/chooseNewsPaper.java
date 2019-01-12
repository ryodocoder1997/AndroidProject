package com.example.hoanglong.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher;

public class chooseNewsPaper extends Activity {
    RadioGroup radioGroup;
    RadioButton rdbVNEx, rdbTuoiTre, rdbDanTri;
    ImageSwitcher sw;
    Button btnStart;

    AlertDialog alertDialog;

    private String ID = "1"; //for Animation Detecting
    private String newsType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosenewspaper);

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroupNSelect);

        rdbVNEx = (RadioButton) findViewById(R.id.RdbVnExpress);
        rdbTuoiTre = (RadioButton) findViewById(R.id.RdbTuoiTre);
        rdbDanTri = (RadioButton) findViewById(R.id.RdbVietNamNet);

        sw = (ImageSwitcher) findViewById(R.id.imgsw);

        btnStart = (Button) findViewById(R.id.BtnStart);

        final Animation inRightToCenter = AnimationUtils.loadAnimation(this, R.anim.img_switch_in_rc);
        final Animation outCenterToLeft = AnimationUtils.loadAnimation(this, R.anim.img_switch_out_cl);

        final Animation inLeftToCenter = AnimationUtils.loadAnimation(this, R.anim.img_switch_in_lc);
        final Animation outCenterToRight = AnimationUtils.loadAnimation(this, R.anim.img_switch_out_cr);

        //Create Factory
        sw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView img = new ImageView(chooseNewsPaper.this);
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return img;
            }
        });
        //Default Image
        sw.setImageResource(R.drawable.vnexpress);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RdbVnExpress: {
                        if (ID == "2") {
                            sw.setInAnimation(inLeftToCenter);
                            sw.setOutAnimation(outCenterToRight);
                            sw.setImageResource(R.drawable.vnexpress);
                            ID = "1";
                        } else if (ID == "3") {
                            sw.setInAnimation(inRightToCenter);
                            sw.setOutAnimation(outCenterToLeft);
                            sw.setImageResource(R.drawable.vnexpress);
                            ID = "1";
                        }
                        }break;
                    case R.id.RdbTuoiTre: {
                        if (ID == "3") {
                            sw.setInAnimation(inLeftToCenter);
                            sw.setOutAnimation(outCenterToRight);
                            sw.setImageResource(R.drawable.tuoitre);
                            ID = "2";
                        } else if (ID == "1") {
                            sw.setInAnimation(inRightToCenter);
                            sw.setOutAnimation(outCenterToLeft);
                            sw.setImageResource(R.drawable.tuoitre);
                            ID = "2";
                        }
                    }break;
                    case R.id.RdbVietNamNet: {
                        if (ID == "1") {
                            sw.setInAnimation(inLeftToCenter);
                            sw.setOutAnimation(outCenterToRight);
                            sw.setImageResource(R.drawable.vietnamnet);
                            ID = "3";
                        } else if (ID == "2") {
                            sw.setInAnimation(inRightToCenter);
                            sw.setOutAnimation(outCenterToLeft);
                            sw.setImageResource(R.drawable.vietnamnet);
                            ID = "3";
                        }
                    }break;
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (haveNetwork()) {
                    int idCheck = radioGroup.getCheckedRadioButtonId();
                    switch (idCheck) {
                        case R.id.RdbVnExpress:
                        {
                            newsType = "VNExpress";
                            Intent vnexpress = new Intent(chooseNewsPaper.this, MainActivity.class);
                            vnexpress.putExtra("newsType", newsType);
                            Log.d("Type", newsType);
                            startActivity(vnexpress);
                        }break;
                        case R.id.RdbTuoiTre:
                        {
                            newsType = "TuoiTre";
                            Intent tuoitre = new Intent(chooseNewsPaper.this, MainActivity.class);
                            tuoitre.putExtra("newsType", newsType);
                            Log.d("Type", newsType);
                            startActivity(tuoitre);
                        }break;
                        case R.id.RdbVietNamNet:
                        {
                            newsType = "VietNamNet";
                            Intent dantri = new Intent(chooseNewsPaper.this, MainActivity.class);
                            dantri.putExtra("newsType", newsType);
                            Log.d("Type", newsType);
                            startActivity(dantri);
                        }break;
                    }
                } else {
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(chooseNewsPaper.this).create();
                    alertDialog.setTitle("CẢNH BÁO");
                        alertDialog.setMessage("Không có Mạng INTERNET, bạn không thể tiếp tục.");
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "OK",
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos)
        {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_WIFI = true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData = true;
        }
        return have_MobileData || have_WIFI;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
