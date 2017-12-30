package com.rahbod.visit365.helper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
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
            else {
                String refreshToken = sessionManager.getRefreshToken();
                if (refreshToken != null) {
                    RefreshToken(refreshToken);
                }
            }
        }
        return null;
    }

    public static String getAccessToken(Context context, String username, String password) {
        mContext = context;
        sessionManager = new SessionManager(context);
        getAuthorize(username, password);
        return null;
    }

    private static String getAuthorize(String username, String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("email", username);
            params.put("password", password);
            AppController.getInstance().sendRequest("oauth/authorize", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            gerAccessTokenFromServer(response.getString("authorization_code"));
                        } else
                            Log.e(ETAG, "Error in get Authorization Code.");
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

    private static String gerAccessTokenFromServer(String authorizationCode) {
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
                            sessionManager.getAccessToken();
                            Log.e(ETAG, sessionManager.getAccessToken());
                        } else
                            Log.e(ETAG, "Get access token error.");
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

    private static String RefreshToken(String refreshToken) {
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
                        } else
                            Log.e(ETAG, "Error refresh token.");
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

    static boolean isExpired(Long expire) {
        return getNowTime() > (expire - 30);
    }

    public static boolean isExpired() {
        return getNowTime() > (sessionManager.getExpireTime()- 30);
    }

    static Long getNowTime() {
        return System.currentTimeMillis() / 1000;
    }
}