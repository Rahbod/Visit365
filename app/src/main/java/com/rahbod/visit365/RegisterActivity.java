package com.rahbod.visit365;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {

    EditText user, password;
    Button register;
    private static final int time =1500;
    private static long BackPressed;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

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
        setContentView(R.layout.register_activity);

        user = (EditText) findViewById(R.id.user_register);
        password = (EditText) findViewById(R.id.password_register);

        register = (Button) findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getText().toString().matches("^[0][0-9]{10}$")){
                    Toast.makeText(RegisterActivity.this, "شماره تلفن نامعتبر است", Toast.LENGTH_SHORT).show();
                }

                else if (user.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "شماره موبایل نمی تواند خالی باشد", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "کلمه عبور نمی تواند خالی باشد", Toast.LENGTH_LONG).show();
                } else
                    register();
            }
        });
    }

    public void goToLogin(View view) {
        finish();
    }

    private void register() {

        register.setEnabled(false);
        register.setText("در حال پردازش...");

        try {

            JSONObject par = new JSONObject();
            par.put("mobile", user.getText().toString());
            par.put("password", password.getText().toString());

            JSONObject params = new JSONObject();
            params.put("user", par);

            AppController.getInstance().sendRequest("api/register", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String toast = response.getString("message");
                        Toast.makeText(RegisterActivity.this, toast, Toast.LENGTH_LONG).show();
                        if (response.getBoolean("status")) {
                            Intent intent = new Intent();
                            intent.putExtra("mobile", user.getText().toString());
                            intent.putExtra("pass", password.getText().toString());
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }else{
                            register.setEnabled(true);
                            register.setText("ثبت نام");
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
