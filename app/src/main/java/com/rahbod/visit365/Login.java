package com.rahbod.visit365;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.rahbod.visit365.helper.SessionManager;

public class Login extends AppCompatActivity {

    EditText user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check user login
        SessionManager sessionManager = new SessionManager(this);
        if(sessionManager.isLoggedIn()){
            Intent index = new Intent(this, Index.class);
            startActivity(index);
            finish();
        }

        // Do Login
        user = (EditText) findViewById(R.id.user_login);
        password = (EditText) findViewById(R.id.password_login);

        Button button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUser = user.getText().toString();
                String strPassword = password.getText().toString();
                AccessTokenHelper.getAccessToken(getApplicationContext(), strUser, strPassword);
            }
        });
    }
}