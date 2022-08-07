package com.example.tabunganku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tabunganku.api.ApiEndPoint;
import com.example.tabunganku.api.ApiService;
import com.example.tabunganku.model.user.UserModel;
import com.example.tabunganku.request.LoginRequest;
import com.example.tabunganku.response.LoginResponse;
import com.example.tabunganku.session.SessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Tanggal Pengerjaan       : 29 Juli 2022
 * NIM                      : 10119918
 * Nama                     : Andreas Suryadi
 * Kelas                    : IF-10K
 *
 */

public class LoginActivity extends AppCompatActivity {
    // initializing views
    private EditText emailText, passwordText;
    private Button loginButton;
    private ProgressBar loadingPb;
    private static final int successCode=200;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // initializing views
        emailText = findViewById(R.id.editTextLoginEmail);
        passwordText = findViewById(R.id.editTextLoginPassword);
        loginButton = findViewById(R.id.btnlogin);
        loadingPb = findViewById(R.id.idLoadingPB);

        sessionManager = new SessionManager(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (emailText.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Field email is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwordText.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Field password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postData(emailText.getText().toString(), passwordText.getText().toString());
            }
        });
    }

    private void postData(String email, String password) {
        loadingPb.setVisibility(View.VISIBLE);

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        LoginRequest loginRequest = new LoginRequest(email, password);

        Call<LoginResponse> call = api.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int statusCode = response.code();
                loadingPb.setVisibility(View.GONE);

                if (statusCode == successCode && response.body().user != null) {
                    emailText.setText("");
                    passwordText.setText("");

                    sessionManager.saveAuthToken(response.body().getAccessToken(), response.body().getUser().getName(), response.body().getUser().getEmail());

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }, 100);
                } else {
                    Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
