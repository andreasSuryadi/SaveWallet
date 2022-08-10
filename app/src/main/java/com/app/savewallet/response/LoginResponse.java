package com.app.savewallet.response;

import com.app.savewallet.model.user.UserModel;
import com.google.gson.annotations.SerializedName;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 3 Agustus 2022
 *
 */

public class LoginResponse {
    @SerializedName("status_code")
    public int statusCode;

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("user")
    public UserModel user;

    public int getStatusCode() {
        return statusCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserModel getUser() {
        return user;
    }
}
