package com.rahbod.visit365.helper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.RequestFuture;
import com.rahbod.visit365.AppController;
import com.rahbod.visit365.DbHelper;
import com.rahbod.visit365.Login;

import org.json.JSONException;
import org.json.JSONObject;

public class AccessTokenHelper {
    private static final String ETAG = "ATH";
    private static SessionManager sessionManager;
    private static Context mContext;

    public static String getAccessToken(Context context) {
        mContext = context;
        sessionManager = new SessionManager(context);
        if (sessionManager.isLoggedIn()) {
            if (!isExpired(sessionManager.getExpireTime()))
                return sessionManager.getAccessToken();
            else
                Log.e(ETAG, "Access token has expired");
        }
        return "";
    }

    public static boolean checkAccessToken(Context context, AppController.VolleyCallback volleyCallback) {
        mContext = context;
        sessionManager = new SessionManager(context);
        if (sessionManager.isLoggedIn()) {
            if (isExpired(sessionManager.getExpireTime())){
                String refreshToken = sessionManager.getRefreshToken();
                if (refreshToken != null) {
                    RefreshToken(refreshToken, volleyCallback);
                }
            }else
                return true;
        }
        return false;
    }

    public static String getAccessToken(Context context, String username, String password, final AppController.VolleyCallback volleyCallback) {
        mContext = context;
        sessionManager = new SessionManager(context);
        getAuthorize(username, password, volleyCallback);
        return null;
    }

    private static String getAuthorize(String username, String password, final AppController.VolleyCallback volleyCallback) {
        JSONObject params = new JSONObject();
        try {
            params.put("mobile", username);
            params.put("password", password);
            AppController.getInstance().sendRequest("oauth/authorize", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e(ETAG + "response", response.toString());
                    try {
                        if (response.getBoolean("status")) {
                            Log.e(ETAG, response.getString("authorization_code"));
                            gerAccessTokenFromServer(response.getString("authorization_code"), volleyCallback);
                        } else {
                            Log.e(ETAG, "Get authorization code error.");
                            volleyCallback.onErrorResponse(response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String gerAccessTokenFromServer(String authorizationCode, final AppController.VolleyCallback volleyCallback) {
        JSONObject params = new JSONObject();
        try {
            params.put("authorization_code", authorizationCode);
            params.put("grant_type", "access_token");
            AppController.getInstance().sendRequest("oauth/token", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            // save in shared preference
                            JSONObject token = response.getJSONObject("token");
                            Log.e(ETAG, token.toString());
                            sessionManager.setToken(token.getString("access_token"), token.getString("refresh_token"), token.getLong("expire_in"));

                            JSONObject user = response.getJSONObject("user");
                            sessionManager.setUserInfo(user.getString("firstName"), user.getString("lastName"), user.getString("mobile"), user.getString("email"), user.getString("phone"), user.getString("address"), user.getString("zipCode"), user.getString("nationalCode"));
                            volleyCallback.onSuccessResponse(sessionManager.getAccessToken());
                        } else {
                            Log.e(ETAG, "Get access token error.");
                            volleyCallback.onErrorResponse(response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void RefreshToken(String refreshToken) {
        RefreshToken(refreshToken, new AppController.VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {

            }

            @Override
            public void onErrorResponse(String result) {

            }
        });
    }

    static void RefreshToken(String refreshToken, final AppController.VolleyCallback volleyCallback) {
        JSONObject params = new JSONObject();
        try {
            params.put("refresh_token", refreshToken);
            params.put("grant_type", "refresh_token");
            AppController.getInstance().sendRequest("oauth/token", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            // update in shared preference
                            JSONObject token = response.getJSONObject("token");
                            long exp = getNowTime() + token.getInt("expire_in");
                            sessionManager.updateToken(token.getString("access_token"), exp);

                            JSONObject user = response.getJSONObject("user");
                            sessionManager.updateUserInfo(user.getString("firstName"), user.getString("lastName"), user.getString("mobile"), user.getString("email"), user.getString("phone"), user.getString("address"), user.getString("zipCode"), user.getString("nationalCode"));

                            volleyCallback.onSuccessResponse(sessionManager.getAccessToken());
                        } else {
                            Log.e(ETAG, "Error refresh token.");
                            volleyCallback.onErrorResponse(response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExpired(Long expire) {
        return getNowTime() > (expire - 30);
    }

    public static boolean isExpired() {
        return getNowTime() > (sessionManager.getExpireTime() - 30);
    }

    public static Long getNowTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static boolean logout(Context context) {
        if (sessionManager == null)
            sessionManager = new SessionManager(context);
        return sessionManager.clear();
    }
}