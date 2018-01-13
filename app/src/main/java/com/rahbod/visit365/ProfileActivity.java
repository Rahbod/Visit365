package com.rahbod.visit365;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.rahbod.visit365.helper.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    TextView drName, drEmail, clinicName, clinicTown, clinicCity, clinicAddress, clinicContracts, clinicFax, clinicPhone;
    TextView memberShipDate, drExp;
    ImageView drAvatar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_dr);

        Bundle bundle = getIntent().getExtras();

        Log.e("bundle= ", bundle.toString());

//      profile
        drName = (TextView) findViewById(R.id.drNameProfile);
        drEmail = (TextView) findViewById(R.id.tvClinicTitle);
        memberShipDate = (TextView) findViewById(R.id.drShipDate);
        drExp = (TextView) findViewById(R.id.drExp);
        drAvatar = (ImageView) findViewById(R.id.drAvatarProfile);

//      data
        clinicName = (TextView) findViewById(R.id.clinicNameProfile);
        clinicTown = (TextView) findViewById(R.id.clinicTown);
        clinicCity = (TextView) findViewById(R.id.clinicCity);
        clinicAddress = (TextView) findViewById(R.id.clinicAddress);
        clinicContracts = (TextView) findViewById(R.id.clinicContracts);
        clinicFax = (TextView) findViewById(R.id.clinicFax);
        clinicPhone = (TextView) findViewById(R.id.clinicPhone);
        try {
            JSONObject params = new JSONObject();
            params.put("doctor_id", bundle.getInt("doctorId"));
            params.put("clinic_id", bundle.getInt("clinicId"));
            Log.e("drid", bundle.getInt("doctorId")+"");

            AppController.getInstance().sendRequest("api/doctorProfile", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            JSONObject doctor = response.getJSONObject("doctor");
                            String drname = doctor.getString("name");
                            String dremail = doctor.getString("email");
                            String membershipdate = doctor.getString("membershipDate");
                            String dravatar = doctor.getString("avatar");

                            PersianDate pdate = new PersianDate(Long.parseLong(membershipdate) * 1000);
                            PersianDateFormat pdformater = new PersianDateFormat("j  F  13y");
                            String str = pdformater.format(pdate);

                            JSONArray Exp = doctor.getJSONArray("expertises");
                            String expertises = "";
                            for (int i = 0; i < Exp.length(); i++) {
                                if (expertises.isEmpty())
                                    expertises += Exp.getString(i);
                                else
                                    expertises += "، " + Exp.getString(i);
                            }

                            drExp.setText(expertises);
                            drName.setText(drname);
                            drEmail.setText(dremail);
                            memberShipDate.setText(str);
                            if (!dravatar.isEmpty()) {
                                Picasso.with(ProfileActivity.this).load(dravatar).into(drAvatar);
                            }

                            JSONObject clinic = response.getJSONObject("clinic");
                            String clinicname = clinic.getString("name");
                            String clinictown = clinic.getString("town");
                            String cliniccity = clinic.getString("city");
                            String clinicaddress = clinic.getString("address");
                            String cliniccontracts = clinic.getString("contracts");
                            String clinicfax = clinic.getString("fax");
                            String clinicphone = clinic.getString("phone");

                            clinicName.setText(clinicname);
                            clinicTown.setText(clinictown);
                            clinicCity.setText(cliniccity);
                            clinicAddress.setText(clinicaddress);
                            clinicContracts.setText(cliniccontracts);
                            clinicFax.setText(clinicfax);
                            clinicPhone.setText(clinicphone);


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

    public void openStep1(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
