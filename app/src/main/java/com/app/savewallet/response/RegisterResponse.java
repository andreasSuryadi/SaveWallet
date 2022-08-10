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

public class RegisterResponse {
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
