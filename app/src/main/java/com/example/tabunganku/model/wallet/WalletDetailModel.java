package com.example.tabunganku.model.wallet;

import com.google.gson.annotations.SerializedName;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 5 Agustus 2022
 *
 */

public class WalletDetailModel {
    @SerializedName("id")
    public int id;
    @SerializedName("wallet_id")
    public String walletId;
    @SerializedName("type")
    public String type;
    @SerializedName("money")
    public String money;
    @SerializedName("description")
    public String description;

    public int getId() {
        return id;
    }

    public String getWalletId() {
        return walletId;
    }

    public String getType() {
        return type;
    }

    public String getMoney() {
        return money;
    }

    public String getDescription() {
        return description;
    }
}
