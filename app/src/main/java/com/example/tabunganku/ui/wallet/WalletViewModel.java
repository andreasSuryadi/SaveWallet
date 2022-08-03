package com.example.tabunganku.ui.wallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 3 Agustus 2022
 *
 */

public class WalletViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WalletViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is wallet fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}