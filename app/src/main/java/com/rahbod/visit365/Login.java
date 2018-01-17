package com.rahbod.visit365;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rahbod.visit365.helper.AccessTokenHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login extends AppCompatActivity {

    EditText user, password;
    Button button_login;
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
        setContentView(R.layout.activity_login);

        TextView txtForget = (TextView) findViewById(R.id.forget_login);
        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        TextView txtRigester = (TextView) findViewById(R.id.register_login);
        txtRigester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RegisterActivity.class);
                startActivityForResult(intent, 123);
            }
        });

        user = (EditText) findViewById(R.id.user_register);
        pd = new ProgressDialog(this);
        pd.setMessage("لطفا صبر کنید ...");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
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
        pd.dismiss();
        // Do Login

        password = (EditText) findViewById(R.id.password_register);

        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUser = user.getText().toString();
                String strPassword = password.getText().toString();
                if(strUser.isEmpty() || strPassword.isEmpty())
                    Toast.makeText(Login.this, "فیلدهای ورود نباید خالی باشند.", Toast.LENGTH_SHORT).show();
                else {
                    if (isNetworkConnected()) {
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
                    }else{
                        Toast.makeText(getApplicationContext(), "No internet access. Please check it.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void afterLogin(){
        Log.e("ATH","logged in");
        Intent index = new Intent(this, Index.class);
        startActivity(index);
        if(pd != null)
            pd.dismiss();
        finish();
    }

    ProgressDialog pd;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK){
            if (isNetworkConnected()) {
                pd = new ProgressDialog(this);
                pd.setMessage("لطفا صبر کنید ...");
                pd.setCancelable(false);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.show();
                button_login.setEnabled(false);
                button_login.setText("در حال انتقال ...");
                AccessTokenHelper.getAccessToken(getApplicationContext(), data.getStringExtra("mobile"), data.getStringExtra("pass"), new AppController.VolleyCallback() {
                    @Override
                    public void onSuccessResponse(String result) {
                        afterLogin();
                    }

                    @Override
                    public void onErrorResponse(String result) {
                        button_login.setEnabled(true);
                        button_login.setText("ورود");
                        pd.dismiss();
                        Toast.makeText(Login.this, result, Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "No internet access. Please check it.", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected() && cm.getActiveNetworkInfo().isAvailable();
    }
}