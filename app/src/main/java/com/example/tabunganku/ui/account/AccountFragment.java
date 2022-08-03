package com.example.tabunganku.ui.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tabunganku.LoginActivity;
import com.example.tabunganku.MainActivity;
import com.example.tabunganku.R;
import com.example.tabunganku.api.ApiEndPoint;
import com.example.tabunganku.api.ApiService;
import com.example.tabunganku.databinding.FragmentAccountBinding;
import com.example.tabunganku.model.user.UserModel;
import com.example.tabunganku.response.LoginResponse;
import com.example.tabunganku.response.UserResponse;
import com.example.tabunganku.session.SessionManager;

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

        getData(nameText, emailText);

        return root;
    }

    private void getData(TextView name, TextView email) {
        ApiService api = ApiEndPoint.getClient().create(ApiService.class);

        sessionManager = new SessionManager(getContext());
        String token = "Bearer " + sessionManager.fetchAuthToken();
        Log.d("token", token);
        Call<UserResponse> call = api.showProfile(token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                int statusCode = response.code();

                if (statusCode == successCode) {
                    UserModel userModel = response.body().getUser();
                    Log.d("name", name.toString());
                    name.setText(userModel.getName());
                    email.setText(userModel.getEmail());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(mainActivity, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}