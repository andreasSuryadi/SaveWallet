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
    public String USER_NAME = "name";
    public String USER_EMAIL = "email";

    public SessionManager(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public void saveAuthToken(String token, String name, String email) {
        editor.putString(USER_TOKEN, token);
        editor.putString(USER_NAME, name);
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    public String fetchAuthToken() {
        return sharedPref.getString(USER_TOKEN, null);
    }

    public String fetchName() {
        return sharedPref.getString(USER_NAME, null);
    }

    public String fetchEmail() {
        return sharedPref.getString(USER_EMAIL, null);
    }

    public void removeAuthToken() {
        editor.clear();
        editor.apply();
    }
}
