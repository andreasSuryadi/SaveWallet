package com.example.tabunganku.response;

import com.example.tabunganku.model.wallet.WalletDetailModel;
import com.example.tabunganku.model.wallet.WalletModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 5 Agustus 2022
 *
 */

public class WalletResponse {
    @SerializedName("status_code")
    public int statusCode;

    @SerializedName("wallet")
    public WalletModel wallet;

    @SerializedName("wallet_details")
    public List<WalletDetailModel> walletDetails;

    public int getStatusCode() {
        return statusCode;
    }

    public WalletModel getWallet() {
        return wallet;
    }

    public void setWalletDetails(List<WalletDetailModel> walletDetails) {
        this.walletDetails = walletDetails;
    }

    public List<WalletDetailModel> getWalletDetails() {
        return walletDetails;
    }
}
