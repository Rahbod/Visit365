package com.rahbod.visit365;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    EditText user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_step2);

//        user = (EditText) findViewById(R.id.user_login);
//        password = (EditText) findViewById(R.id.password_login);
//
//        Button button_login = (Button) findViewById(R.id.button_login);
//        button_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String strUser = user.getText().toString();
//                String strPassword = password.getText().toString();
//                getAuthorize(strUser, strPassword);
//            }
//        });
    }

    private void getAuthorize(String user, String pass) {
        JSONObject params = new JSONObject();
        try {
            params.put("email", user);
            params.put("password", pass);
            AppController.getInstance().sendRequest("oauth/authorize", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.e("re", response.toString());
                        if (response.getBoolean("status")) {
                            gerAccessToken(response.getString("authorization_code"));
                        } else
                            Toast.makeText(Login.this, "Error in get Authorization Code.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void gerAccessToken(String ac) {
        JSONObject params = new JSONObject();
        try {
            params.put("authorization_code", ac);
            params.put("grant_type", "access_token");
            AppController.getInstance().sendRequest("oauth/token", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            // save in shared preference
                            JSONObject token = response.getJSONObject("token");
                            token.getString("access_token");
                            token.getString("refresh_token");
                            token.getInt("expire_in");
                        } else
                            Toast.makeText(Login.this, "Error in get Token.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refreshAccessToken(String rt) {
        JSONObject params = new JSONObject();
        try {
            params.put("refresh_token", rt);
            params.put("grant_type", "refresh_token");
            AppController.getInstance().sendRequest("oauth/token", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            // update in shared preference
                            JSONObject token = response.getJSONObject("token");
                            token.getString("access_token");
                            token.getInt("expire_in");
                        } else
                            Toast.makeText(Login.this, "Error in Refresh Token.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Long getNowTime() {
        return System.currentTimeMillis() / 1000;
    }
}