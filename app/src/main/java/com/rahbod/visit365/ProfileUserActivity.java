package com.rahbod.visit365;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileUserActivity extends AppCompatActivity {
    HashMap<String, String> userInfo;
    EditText etNationalCode;
    EditText etFirstName;
    EditText etLastName;
    EditText etZipCode;
    EditText etMobile;
    EditText etPhone;
    EditText etAddress;
    Button btnSave;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        userInfo = SessionManager.getUserInfo(this);

        etNationalCode = (EditText) findViewById(R.id.etNationalCode);
        etNationalCode.setText(userInfo.get("nationalCode"));

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etFirstName.setText(userInfo.get("firstName"));

        etLastName = (EditText) findViewById(R.id.etLastName);
        etLastName.setText(userInfo.get("lastName"));

        etMobile = (EditText) findViewById(R.id.etMobile);
        etMobile.setText(userInfo.get("mobile"));

        etPhone= (EditText) findViewById(R.id.etPhone);
        etPhone.setText(userInfo.get("phone"));

        etAddress = (EditText) findViewById(R.id.etAddress);
        etAddress.setText(userInfo.get("address"));

        etZipCode= (EditText) findViewById(R.id.etZipCode);
        etZipCode.setText(userInfo.get("zipCode"));

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setText("در حال ثبت...");
                btnSave.setEnabled(false);

                try {
                    JSONObject params = new JSONObject();

                    JSONObject objparams = new JSONObject();

                    objparams.put("first_name", etFirstName.getText().toString());
                    objparams.put("last_name", etLastName.getText().toString());
                    objparams.put("phone", etPhone.getText().toString());
                    objparams.put("mobile", etMobile.getText().toString());
                    objparams.put("zip_code", etZipCode.getText().toString());
                    objparams.put("address", etAddress.getText().toString());
                    objparams.put("national_code", etNationalCode.getText().toString());

                    params.put("profile", objparams);

                    AppController.getInstance().sendAuthRequest("api/editProfile", params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("status")){
                                    String message = response.getString("message");

                                    SessionManager sessionManager = new SessionManager(ProfileUserActivity.this);
                                    JSONObject user = response.getJSONObject("user");
                                    sessionManager.updateUserInfo(user.getString("firstName"), user.getString("lastName"), user.getString("mobile"), user.getString("email"), user.getString("phone"), user.getString("address"), user.getString("zipCode"), user.getString("nationalCode"));

                                    btnSave.setText("ثبت");
                                    btnSave.setEnabled(true);

                                    Toast.makeText(ProfileUserActivity.this, message, Toast.LENGTH_LONG).show();
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
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Index.class);
        startActivity(intent);
        finish();
    }

    public void goToIndex_ProfileUser(View view) {
        Intent intent = new Intent(this, Index.class);
        startActivity(intent);
        finish();
    }
}
