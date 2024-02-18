package com.ama.trainingtask.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ama.trainingtask.R;
import com.ama.trainingtask.view_model.UserManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            if (UserManager.getInstance(SplashScreenActivity.this).isLoggedIn()) {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}