package com.rahbod.visit365;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetActivity extends AppCompatActivity {

    Button btnSms;
    EditText user;
    private static final int time =1500;
    private static long BackPressed;

    @Override
    public void onBackPressed() {
        if (time + BackPressed>System.currentTimeMillis()){
            super.onBackPressed();
        }
        else
            Toast.makeText(this, "لطفا کلید برگشت را مجددا فشار دهید.", Toast.LENGTH_SHORT).show();

        BackPressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        btnSms = (Button) findViewById(R.id.button_forget);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void SendSms(View view) {

        //forget password
        user = (EditText) findViewById(R.id.user_forget);
        if (!user.getText().toString().matches("^[0][0-9]{10}$")) {
            Toast.makeText(ForgetActivity.this, "شماره تلفن نامعتبر است", Toast.LENGTH_SHORT).show();
        } else {
            try {
                btnSms.setEnabled(false);
                btnSms.setText("در حال پردازش...");


                JSONObject params = new JSONObject();
                params.put("mobile", user.getText().toString());

                AppController.getInstance().sendRequest("api/forgetPassword", params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("abcd", response.toString());
                        try {
                            String forget = response.getString("message");
                            Toast.makeText(ForgetActivity.this, forget, Toast.LENGTH_LONG).show();
                            btnSms.setEnabled(true);
                            btnSms.setText("ارسال");
                            if (response.getBoolean("status")) {
                                Intent intent = new Intent(ForgetActivity.this, Login.class);
                                startActivity(intent);
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
    }
}
