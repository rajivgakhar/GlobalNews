package com.rajiv.a300269668.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

public class ViewNewsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);
        setupToolbarMenu();

        Intent in=getIntent();

        WebView ourBrow=(WebView) findViewById(R.id.webview);
        ourBrow.getSettings().setJavaScriptEnabled(true);
        ourBrow.getSettings().setLoadWithOverviewMode(true);
        ourBrow.getSettings().setUseWideViewPort(true);
        ourBrow.setWebViewClient(new onViewClient());
        ourBrow.loadUrl(in.getStringExtra("url"));
    }
    private void setupToolbarMenu(){
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("News Zone");
        mToolbar.setNavigationIcon(R.drawable.back_arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewNewsActivity.this,HomeActivity.class));
                finish();
            }
        });

    }
}
