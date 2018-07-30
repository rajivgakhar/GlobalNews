package com.rajiv.a300269668.newsapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView splashProgreeBar,global,news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

      splashProgreeBar=(TextView) findViewById(R.id.txtProgress);
        global=(TextView) findViewById(R.id.global);
        news=(TextView) findViewById(R.id.news);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);

        global.startAnimation(animation);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide1);

        news.startAnimation(animation1);

        new Thread(new Runnable() {

            public void run() {
                int i = 0;
                while (i < 100) {
                    SystemClock.sleep(18);
                    i++;
                    final int curCount = i;
                    if (curCount % 5 == 0) {
                        //update UI with progress every 5%
                        splashProgreeBar.post(new Runnable() {
                            public void run() {
                                splashProgreeBar.setText(curCount+"%");
                            }
                        });
                    }
                }
                splashProgreeBar.post(new Runnable() {
                    public void run() {
                        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                        finish();
                    }
                });
            }
        }).start();
    }
}
