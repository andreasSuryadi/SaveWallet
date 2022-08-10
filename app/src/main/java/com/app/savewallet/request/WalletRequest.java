package com.app.savewallet.request;

import com.google.gson.annotations.SerializedName;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 8 Agustus 2022
 *
 */

public class WalletRequest {
    @SerializedName("type")
    public String type;
    @SerializedName("money")
    public String money;
    @SerializedName("description")
    public String description;

    public WalletRequest(String type, String money, String description) {
        this.type = type.toLowerCase();
        this.money = money;
        this.description = description;
    }
}
