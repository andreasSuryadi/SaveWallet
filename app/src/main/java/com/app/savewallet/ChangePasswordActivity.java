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
import com.app.savewallet.request.ChangePasswordRequest;
import com.app.savewallet.response.ChangePasswordResponse;
import com.app.savewallet.session.SessionManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Tanggal Pengerjaan       : 4 Agustus 2022
 * NIM                      : 10119918
 * Nama                     : Andreas Suryadi
 * Kelas                    : IF-10K
 *
 */

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText currentPasswordText, newPasswordText, newPasswordConfirmationText;
    private Button btnChangePassword;
    private ProgressBar loadingPb;
    private static final int successCode=200;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

        currentPasswordText = findViewById(R.id.currentPassword);
        newPasswordText = findViewById(R.id.newPassword);
        newPasswordConfirmationText = findViewById(R.id.newPasswordConfirmation);
        btnChangePassword = findViewById(R.id.btn_change_password);
        loadingPb = findViewById(R.id.idLoadingPB);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (currentPasswordText.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Field name is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newPasswordText.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Field email is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newPasswordConfirmationText.getText().toString().isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Field email is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(currentPasswordText.getText().toString(), newPasswordText.getText().toString(), newPasswordConfirmationText.getText().toString());
            }
        });
    }

    private void postData(String currentPassword, String newPassword, String newPasswordConfirmation) {
        loadingPb.setVisibility(View.VISIBLE);

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(currentPassword, newPassword, newPasswordConfirmation);
        sessionManager = new SessionManager(this);
        String token = "Bearer " + sessionManager.fetchAuthToken();

        Call<ChangePasswordResponse> call = api.changePassword(token, changePasswordRequest);
        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                int statusCode = response.code();
                loadingPb.setVisibility(View.GONE);

                if (statusCode == successCode && response.body().user != null) {
                    currentPasswordText.setText("");
                    newPasswordText.setText("");
                    newPasswordConfirmationText.setText("");

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                        intent.putExtra("Fragment", "Account_Fragment");
                        startActivity(intent);
                        finish();
                    }, 100);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
