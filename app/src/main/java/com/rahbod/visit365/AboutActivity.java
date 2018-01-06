package com.rahbod.visit365;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.helper.AccessTokenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    private static final int time =1500;
    private static long BackPressed;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setContentView(R.layout.navigationdraw_about);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_about);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationViewAbout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.credit_card_NavigationView:
                        Intent goTransactions = new Intent(AboutActivity.this, TransactionActivity.class);
                        startActivity(goTransactions);
                        finish();
                        break;

                    case R.id.help_NavigationView:
                        Intent goHelp = new Intent(AboutActivity.this, HelpActivity.class);
                        startActivity(goHelp);
                        finish();
                        break;

                    case R.id.user_NavigationView:
                        Intent goProfile = new Intent(AboutActivity.this, ProfileUserActivity.class);
                        startActivity(goProfile);
                        finish();
                        break;

                    case R.id.abut_NavigationView:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;

                    case R.id.home_NavigationView:
                        Intent goAbout = new Intent(AboutActivity.this, Index.class);
                        startActivity(goAbout);
                        finish();
                        break;
                }

                return true;
            }
        });


        final TextView txtWebView = (TextView) findViewById(R.id.webViewHelp);

        try {
            JSONObject params = new JSONObject();
            params.put("name", "about");

            AppController.getInstance().sendRequest("api/page", params, new Response.Listener<JSONObject>() {
                @SuppressLint("NewApi")
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")){
                            String strHtml = response.getString("text");

                            txtWebView.setText(Html.fromHtml(strHtml, Html.FROM_HTML_MODE_COMPACT));
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

    public void openNvAbout(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
        drawerLayout.findViewById(R.id.btnExitAbout).setOnClickListener(new View.OnClickListener() {
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
