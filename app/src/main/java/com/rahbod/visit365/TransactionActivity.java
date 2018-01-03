package com.rahbod.visit365;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.rahbod.visit365.helper.SessionManager;

import org.json.JSONObject;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);
SessionManager sessionManager = new SessionManager(this);
        Log.e("ResponseError", sessionManager.isLoggedIn()+"");
        Log.e("ResponseError", sessionManager.getAccessToken()+"");
        AppController.getInstance().sendAuthRequest("api/transactions", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("abcd", response.toString());
            }
        });
    }
}
