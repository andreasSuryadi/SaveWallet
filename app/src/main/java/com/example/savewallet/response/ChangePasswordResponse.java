package com.example.savewallet.response;

import com.example.savewallet.model.user.UserModel;
import com.google.gson.annotations.SerializedName;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 4 Agustus 2022
 *
 */

public class ChangePasswordResponse {
    @SerializedName("status_code")
    public int statusCode;

    @SerializedName("user")
    public UserModel user;

    public int getStatusCode() {
        return statusCode;
    }

    public UserModel getUser() {
        return user;
    }
}
