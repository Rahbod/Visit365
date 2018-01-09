package com.rahbod.visit365.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences LoginPref;
    Editor loginEditor;

    SharedPreferences UserPref;
    Editor userEditor;

    Context _context;

    // Shared pref mode
    static int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Visit365Login";
    private static final String PREF_USER = "Visit365UserInfo";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        // Login and Access Token Shared Preference
        LoginPref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        loginEditor = LoginPref.edit();
        // User Info Shared Preference
        UserPref = _context.getSharedPreferences(PREF_USER, PRIVATE_MODE);
        userEditor = UserPref.edit();
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

    public void setUserInfo(String firstName, String lastName, String mobile, String email, String phone, String address, String zipCode, String nationalCode) {
        userEditor.putString("firstName", firstName);
        userEditor.putString("lastName", lastName);
        userEditor.putString("mobile", mobile);
        userEditor.putString("email", email);
        userEditor.putString("phone", phone);
        userEditor.putString("address", address);
        userEditor.putString("zipCode", zipCode);
        userEditor.putString("nationalCode", nationalCode);
        // commit changes
        userEditor.commit();
        Log.d(TAG, "User Info modified!");
    }

    public void updateUserInfo(String firstName, String lastName, String mobile, String email, String phone, String address, String zipCode, String nationalCode) {
        userEditor.putString("firstName", firstName);
        userEditor.putString("lastName", lastName);
        userEditor.putString("mobile", mobile);
        userEditor.putString("email", email);
        userEditor.putString("phone", phone);
        userEditor.putString("address", address);
        userEditor.putString("zipCode", zipCode);
        userEditor.putString("nationalCode", nationalCode);
        // commit changes
        userEditor.commit();
        Log.d(TAG, "User Info modified!");
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

    public boolean clear() {
        loginEditor.clear();
        loginEditor.commit();
        userEditor.clear();
        userEditor.commit();
        return true;
    }

    public static HashMap<String, String> getUserInfo(Context context) {
        SharedPreferences UserPref = context.getSharedPreferences(PREF_USER, PRIVATE_MODE);
        HashMap<String, String> userInfo = new HashMap<>(8);
        userInfo.put("firstName", UserPref.getString("firstName", ""));
        userInfo.put("lastName", UserPref.getString("lastName", ""));
        userInfo.put("mobile", UserPref.getString("mobile", ""));
        userInfo.put("email", UserPref.getString("email", ""));
        userInfo.put("phone", UserPref.getString("phone", ""));
        userInfo.put("address", UserPref.getString("address", ""));
        userInfo.put("zipCode", UserPref.getString("zipCode", ""));
        userInfo.put("nationalCode", UserPref.getString("nationalCode", ""));
        return userInfo;
    }


    public static boolean validUserInfo(Context context) {
        HashMap<String, String> userInfo = SessionManager.getUserInfo(context);
        return !(userInfo.get("firstName").isEmpty() ||
                userInfo.get("lastName").isEmpty() ||
                userInfo.get("mobile").isEmpty() ||
                userInfo.get("email").isEmpty() ||
                userInfo.get("phone").isEmpty() ||
                userInfo.get("address").isEmpty() ||
                userInfo.get("zipCode").isEmpty() ||
                userInfo.get("nationalCode").isEmpty());
    }
}