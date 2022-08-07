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

public class WalletModel {
    @SerializedName("id")
    public int id;
    @SerializedName("user_id")
    public String userId;
    @SerializedName("total_wallet")
    public String totalWallet;
    @SerializedName("total_wallet_income")
    public String totalWalletIncome;
    @SerializedName("total_wallet_expense")
    public String totalWalletExpense;
    @SerializedName("created_at")
    public String createdAt;

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTotalWallet() {
        return totalWallet;
    }

    public String getTotalWalletIncome() {
        return totalWalletIncome;
    }

    public String getTotalWalletExpense() {
        return totalWalletExpense;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
