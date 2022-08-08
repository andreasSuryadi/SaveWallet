package com.example.tabunganku.api;

import com.example.tabunganku.request.ChangePasswordRequest;
import com.example.tabunganku.request.EditProfileRequest;
import com.example.tabunganku.request.LoginRequest;
import com.example.tabunganku.request.WalletRequest;
import com.example.tabunganku.response.ChangePasswordResponse;
import com.example.tabunganku.response.EditProfileResponse;
import com.example.tabunganku.response.LoginResponse;
import com.example.tabunganku.model.user.UserModel;
import com.example.tabunganku.response.LogoutResponse;
import com.example.tabunganku.response.UserResponse;
import com.example.tabunganku.response.WalletResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    // For logout
    @POST("api/logout")
    Call<LogoutResponse> logout(@Header("Authorization") String token);

    // For show user profile
    @GET("api/user/show-profile")
    Call<UserResponse> showProfile(@Header("Authorization") String token);

    // For edit user profile
    @POST("api/user/edit-profile")
    Call<EditProfileResponse> editProfile(@Header("Authorization") String token, @Body EditProfileRequest editProfileRequest);

    // For user change password
    @POST("api/user/change-password")
    Call<ChangePasswordResponse> changePassword(@Header("Authorization") String token, @Body ChangePasswordRequest changePasswordRequest);

    // For list wallet
    @GET("api/wallet")
    Call<WalletResponse> wallet(@Header("Authorization") String token, @Query("start_date") Date startDate, @Query("start_date") Date endDate);

    @POST("api/wallet/store")
    Call<WalletResponse> saveWallet(@Header("Authorization") String token, @Body WalletRequest walletRequest);
}
