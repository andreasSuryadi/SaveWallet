package com.example.tabunganku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tabunganku.api.ApiEndPoint;
import com.example.tabunganku.api.ApiService;
import com.example.tabunganku.model.user.UserModel;
import com.example.tabunganku.request.EditProfileRequest;
import com.example.tabunganku.response.EditProfileResponse;
import com.example.tabunganku.response.UserResponse;
import com.example.tabunganku.session.SessionManager;

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

public class EditProfileActivity extends AppCompatActivity {
    private EditText nameText, emailText;
    private Button btnEditProfile;
    private ProgressBar loadingPb;
    private static final int successCode=200;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_proflie);
        Objects.requireNonNull(getSupportActionBar()).hide();

        nameText = findViewById(R.id.editTextRegisterName);
        emailText = findViewById(R.id.editTextRegisterEmail);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        loadingPb = findViewById(R.id.idLoadingPB);

        getData(nameText, emailText);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (nameText.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Field name is empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (emailText.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Field email is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(nameText.getText().toString(), emailText.getText().toString());
            }
        });
    }

    private void getData(TextView name, TextView email) {
        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        sessionManager = new SessionManager(this);
        String token = "Bearer " + sessionManager.fetchAuthToken();
        Call<UserResponse> call = api.showProfile(token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int statusCode = response.code();

                if (statusCode == successCode) {
                    UserModel userModel = response.body().getUser();
                    name.setText(userModel.getName());
                    email.setText(userModel.getEmail());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postData(String name, String email) {
        loadingPb.setVisibility(View.VISIBLE);

        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        EditProfileRequest editProfileRequest = new EditProfileRequest(name, email);
        sessionManager = new SessionManager(this);
        String token = "Bearer " + sessionManager.fetchAuthToken();

        Call<EditProfileResponse> call = api.editProfile(token, editProfileRequest);
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                int statusCode = response.code();
                loadingPb.setVisibility(View.GONE);

                if (statusCode == successCode && response.body().user != null) {
                    nameText.setText("");
                    emailText.setText("");
                    sessionManager.saveEditProfile(name, email);

                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                        intent.putExtra("Fragment", "Account_Fragment");
                        startActivity(intent);
                        finish();
                    }, 100);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
