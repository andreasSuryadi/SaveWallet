package com.app.savewallet.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.savewallet.ChangePasswordActivity;
import com.app.savewallet.EditProfileActivity;
import com.app.savewallet.MainActivity;
import com.app.savewallet.R;
import com.app.savewallet.SplashActivity;
import com.app.savewallet.api.ApiEndPoint;
import com.app.savewallet.api.ApiService;
import com.app.savewallet.databinding.FragmentAccountBinding;
import com.app.savewallet.response.LogoutResponse;
import com.app.savewallet.session.SessionManager;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 3 Agustus 2022
 *
 */

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    MainActivity mainActivity = new MainActivity();
    private static final int successCode=200;
    SessionManager sessionManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView nameText = binding.profileName;
        TextView emailText = binding.profileEmail;
        Button btnEditProfile = binding.btnEditProfile;
        Button btnChangePassword = binding.btnChangePassword;
        Button btnLogout = binding.btnLogout;

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 logout();
             }
         });

        getData(nameText, emailText);

        return root;
    }

    private void logout() {
        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        sessionManager = new SessionManager(getContext());
        String token = "Bearer " + sessionManager.fetchAuthToken();
        Call<LogoutResponse> call = api.logout(token);
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                int statusCode = response.code();

                if (statusCode == successCode) {
                    sessionManager.removeAuthToken();
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(getActivity(), SplashActivity.class);
                        startActivity(intent);
                    }, 100);
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(TextView name, TextView email) {
        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        sessionManager = new SessionManager(getContext());
        name.setText(sessionManager.fetchName());
        email.setText(sessionManager.fetchEmail());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}