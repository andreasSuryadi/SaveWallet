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

public class ChangePasswordRequest {
    @SerializedName("current_password")
    public String currentPassword;
    @SerializedName("new_password")
    public String newPassword;
    @SerializedName("new_password_confirmation")
    public String newPasswordConfirmation;

    public ChangePasswordRequest(String currentPassword, String newPassword, String newPasswordConfirmation) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}
