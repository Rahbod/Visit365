package com.rahbod.visit365;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class TrackingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        ImageView btnBackToIndex= (ImageView) findViewById(R.id.btnBackToIndex);
        btnBackToIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnTracking = (Button) findViewById(R.id.btnTracking);
        btnTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextView user  = (TextView) findViewById(R.id.user);
        final TextView mobile = (TextView) findViewById(R.id.mobile);
        final TextView nationalCode= (TextView) findViewById(R.id.nationalCode);
        final TextView clinicId = (TextView) findViewById(R.id.clinicId);
        final TextView doctorId = (TextView) findViewById(R.id.doctorId);
        final TextView expertiseId = (TextView) findViewById(R.id.expertiseId);
        final TextView dateStep3 = (TextView) findViewById(R.id.dateStep3);
        final TextView timeStep3 = (TextView) findViewById(R.id.timeStep3);
        final TextView trackingCode = (TextView) findViewById(R.id.trackingCode);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        JSONObject params = new JSONObject();
        try {
            params.put("id", id);
            AppController.getInstance().sendAuthRequest("api/visitInfo", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            JSONObject visitObject = response.getJSONObject("visit");
                            String Clinic = visitObject.getString("clinic");
                            String Doctor = visitObject.getString("doctor");
                            String Expertise = visitObject.getString("expertise");
                            String Date = visitObject.getString("date");
                            String DoctorTime = visitObject.getString("doctorTime");
                            String TrackingCode = visitObject.getString("trackingCode");

                            JSONObject userObject = visitObject.getJSONObject("user");
                            String Name = userObject.getString("name");
                            String NationalCode = userObject.getString("nationalCode");
                            String Mobile = userObject.getString("mobile");

                            PersianDate pdate = new PersianDate(Long.parseLong(Date) * 1000);
                            PersianDateFormat pdformater = new PersianDateFormat("j  F  13y");
                            String strData = pdformater.format(pdate);

                            user.setText(Name);
                            mobile.setText(Mobile);
                            nationalCode.setText(NationalCode);
                            clinicId.setText(Clinic);
                            doctorId.setText(Doctor);
                            expertiseId.setText(Expertise);
                            dateStep3.setText(strData);
                            timeStep3.setText(DoctorTime);
                            trackingCode.setText(TrackingCode);
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
