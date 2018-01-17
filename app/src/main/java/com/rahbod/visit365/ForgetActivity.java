package com.rahbod.visit365;

import android.content.Context;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgetActivity extends AppCompatActivity {

    Button btnSms;
    EditText user;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        TextView txtlogin_forget = (TextView) findViewById(R.id.login_forget);
        txtlogin_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSms = (Button) findViewById(R.id.button_forget);
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
