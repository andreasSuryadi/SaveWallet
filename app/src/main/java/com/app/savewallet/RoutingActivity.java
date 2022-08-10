package com.app.savewallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.savewallet.session.SessionManager;

import java.util.Objects;

/**
 *
 * Tanggal Pengerjaan       : 2 Agustus 2022
 * NIM                      : 10119918
 * Nama                     : Andreas Suryadi
 * Kelas                    : IF-10K
 *
 */

public class RoutingActivity extends AppCompatActivity {
//    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkFirstRun();
    }

    private void checkFirstRun() {
        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.fetchAuthToken();

        setContentView(R.layout.splash_screen);

        if (token == null) {
            startActivity();
        } else {
            Objects.requireNonNull(getSupportActionBar()).hide();

            new Handler().postDelayed(() -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        }
    }

    private void startActivity() {
        Objects.requireNonNull(getSupportActionBar()).hide();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
