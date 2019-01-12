package com.example.hoanglong.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShowWeb extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showweb);

        webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        String linkpath = intent.getStringExtra("link");
        webView.loadUrl(linkpath);
        webView.setWebViewClient(new WebViewClient());
    }
}
