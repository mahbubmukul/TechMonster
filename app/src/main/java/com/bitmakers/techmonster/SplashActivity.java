package com.bitmakers.techmonster;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        thread.start();


    }

}
