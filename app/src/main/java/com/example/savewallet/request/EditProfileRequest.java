package com.example.savewallet.request;

import com.google.gson.annotations.SerializedName;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 4 Agustus 2022
 *
 */

public class EditProfileRequest {
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;

    public EditProfileRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
