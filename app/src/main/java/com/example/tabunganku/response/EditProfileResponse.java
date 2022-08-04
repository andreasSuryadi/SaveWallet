package com.example.tabunganku.response;

import com.example.tabunganku.model.user.UserModel;
import com.google.gson.annotations.SerializedName;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 4 Agustus 2022
 *
 */

public class EditProfileResponse {
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
