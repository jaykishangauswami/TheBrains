package com.the.brain.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.the.brain.R;


public class SplashActivity extends RuntimePermissionsActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.super.requestAppPermissions(new
                                String[]{android.Manifest.permission.READ_PHONE_STATE,
                                android.Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.ACCESS_WIFI_STATE},
                        R.string.runtime_permissions_txt
                        , 20);

            }
        },SPLASH_TIME_OUT)    ;
        //new PrefetchData().execute();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(i);
        finish();

    }
}
