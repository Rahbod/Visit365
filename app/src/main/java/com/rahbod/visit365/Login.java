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
import com.android.volley.toolbox.ImageLoader;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.rahbod.visit365.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText user, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.user_register);

        // check user login
        if (AccessTokenHelper.checkAccessToken(this,new AppController.VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) {
                afterLogin();
            }

            @Override
            public void onErrorResponse(String result) {
                Log.e("LOGIN", "Refresh token error!");
            }
        })) {
            afterLogin();
        }

        // Do Login

        password = (EditText) findViewById(R.id.password_register);

        final Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUser = user.getText().toString();
                String strPassword = password.getText().toString();
                if(strUser.isEmpty() || strPassword.isEmpty())
                    Toast.makeText(Login.this, "فیلدهای ورود نباید خالی باشند.", Toast.LENGTH_SHORT).show();
                else {
                    button_login.setEnabled(false);
                    button_login.setText("در حال انتقال ...");
                    AccessTokenHelper.getAccessToken(getApplicationContext(), strUser, strPassword, new AppController.VolleyCallback() {
                        @Override
                        public void onSuccessResponse(String result) {
                            Log.e("ATH", "login");
                            afterLogin();
                        }

                        @Override
                        public void onErrorResponse(String result) {
                            button_login.setEnabled(true);
                            button_login.setText("ورود");
                            Toast.makeText(Login.this, result, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    public void afterLogin(){
        Log.e("ATH","logged in");
        Intent index = new Intent(this, TransactionActivity.class);
        startActivity(index);
        finish();
    }

    public void goToRegister(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToForget(View view) {
        Intent intent = new Intent(this, ForgetActivity.class);
        startActivity(intent);
    }
}