package com.app.savewallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.savewallet.api.ApiEndPoint;
import com.app.savewallet.api.ApiService;
import com.app.savewallet.request.RegisterRequest;
import com.app.savewallet.response.RegisterResponse;

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

public class RegisterActivity extends AppCompatActivity {
    private EditText nameText, emailText, passwordText, passwordConfirmationText;
    private Button registerButton;
    private ProgressBar loadingPb;
    private static final int successCode=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // initializing views
        nameText = findViewById(R.id.editTextRegisterName);
        emailText = findViewById(R.id.editTextRegisterEmail);
        passwordText = findViewById(R.id.editTextRegisterPassword);
        passwordConfirmationText = findViewById(R.id.editTextRegisterPasswordConfirmation);
        registerButton = findViewById(R.id.btnregister);
        loadingPb = findViewById(R.id.idLoadingPB);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (nameText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Field name is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (emailText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Field email is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwordText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Field password is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (passwordConfirmationText.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Field password confirmation is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!passwordText.getText().toString().equals(passwordConfirmationText.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Password confirmation is not same", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postData(nameText.getText().toString(), emailText.getText().toString(), passwordText.getText().toString(), passwordConfirmationText.getText().toString());
            }
        });
    }

    private void postData(String name, String email, String password, String passwordConfirmation) {
        loadingPb.setVisibility(View.VISIBLE);

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        RegisterRequest registerRequest = new RegisterRequest(name, email, password, passwordConfirmation);

        Call<RegisterResponse> call = api.register(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                int statusCode = response.code();
                loadingPb.setVisibility(View.GONE);

                nameText.setText("");
                emailText.setText("");
                passwordText.setText("");
                passwordConfirmationText.setText("");

                if (statusCode == successCode) {
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
                        startActivity(intent);
                        finish();
                    }, 100);
                } else {
                    Toast.makeText(RegisterActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
