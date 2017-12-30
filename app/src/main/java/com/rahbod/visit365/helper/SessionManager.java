package com.rahbod.visit365.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences LoginPref;
    Editor loginEditor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Visit365Login";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        LoginPref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        loginEditor = LoginPref.edit();
    }

    public void setToken(String accessToken, String refreshToken, Long expireIn) {
        loginEditor.putString("accessToken", accessToken);
        loginEditor.putString("refreshToken", refreshToken);
        loginEditor.putLong("expireIn", expireIn);
        loginEditor.putBoolean(KEY_IS_LOGGEDIN, true);
        // commit changes
        loginEditor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public void updateToken(String accessToken, Long expireIn) {
        loginEditor.putString("accessToken", accessToken);
        loginEditor.putLong("expireIn", expireIn);
        loginEditor.putBoolean(KEY_IS_LOGGEDIN, true);
        // commit changes
        loginEditor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return LoginPref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getAccessToken() {
        return LoginPref.getString("accessToken", null);
    }

    public String getRefreshToken() {
        return LoginPref.getString("refreshToken", null);
    }

    public Long getExpireTime() {
        return LoginPref.getLong("expireIn", 0);
    }
}