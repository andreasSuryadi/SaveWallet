package com.example.tabunganku.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tabunganku.R;

/**
 *
 * NIM                  : 10119918
 * Nama                 : Andreas Suryadi
 * Kelas                : IF-10K
 * Tanggal Pengerjaan   : 3 Agustus 2022
 *
 */

public class SessionManager {
    private final Context context;
    private final SharedPreferences sharedPref;
    private final SharedPreferences.Editor editor;

    public String USER_TOKEN = "user_token";

    public SessionManager(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void saveAuthToken(String token) {
        Log.d("token", token);
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String fetchAuthToken() {
        return sharedPref.getString(USER_TOKEN, null);
    }
}
