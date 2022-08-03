package com.example.tabunganku.api;

import com.example.tabunganku.request.LoginRequest;
import com.example.tabunganku.response.LoginResponse;
import com.example.tabunganku.model.user.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 2 Agustus 2022
 *
 */

public interface ApiService {
    // For register
    @POST("api/register")
    Call<UserModel> register(@Body UserModel userModel);

    // For login
    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
