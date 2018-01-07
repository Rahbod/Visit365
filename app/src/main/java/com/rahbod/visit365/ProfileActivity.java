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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    TextView drName, drEmail, clinicName, clinicTown, clinicCity, clinicAddress, clinicContracts, clinicFax, clinicPhone;
    TextView memberShipDate, drExp;
    ImageView drAvatar;
    private static final int time =1500;
    private static long BackPressed;
    DrawerLayout drawerLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_dr);
        setContentView(R.layout.navigationdraw_profile_dr);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_profile_dr);

        Bundle bundle = getIntent().getExtras();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nvProfileDr);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.credit_card_NavigationView:
                        Intent goTransactions = new Intent(ProfileActivity.this, TransactionActivity.class);
                        startActivity(goTransactions);
                        break;

                    case R.id.help_NavigationView:
                        Intent goHelp = new Intent(ProfileActivity.this, HelpActivity.class);
                        startActivity(goHelp);
                        break;

                    case R.id.user_NavigationView:
                        Intent goProfile = new Intent(ProfileActivity.this, ProfileUserActivity.class);
                        startActivity(goProfile);
                        break;

                    case R.id.abut_NavigationView:
                        Intent goAbout = new Intent(ProfileActivity.this, AboutActivity.class);
                        startActivity(goAbout);
                        break;

                    case R.id.home_NavigationView:
                        Intent goHome = new Intent(ProfileActivity.this, Index.class);
                        startActivity(goHome);
                        break;
                }

                return true;
            }
        });

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

    public void openNvProfileDr(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
        drawerLayout.findViewById(R.id.btnExitProfileDr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessTokenHelper.logout(getApplicationContext());
                restart();
            }
        });
    }

    public void restart() {
        Intent intent = new Intent(this, Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        finish();
        System.exit(2);
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
}
