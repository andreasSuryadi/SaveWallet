package com.app.savewallet.api;

import com.app.savewallet.request.ChangePasswordRequest;
import com.app.savewallet.request.EditProfileRequest;
import com.app.savewallet.request.LoginRequest;
import com.app.savewallet.request.RegisterRequest;
import com.app.savewallet.request.WalletRequest;
import com.app.savewallet.response.ChangePasswordResponse;
import com.app.savewallet.response.EditProfileResponse;
import com.app.savewallet.response.LoginResponse;
import com.app.savewallet.response.LogoutResponse;
import com.app.savewallet.response.RegisterResponse;
import com.app.savewallet.response.UserResponse;
import com.app.savewallet.response.WalletResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

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
