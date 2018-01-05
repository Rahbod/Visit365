package com.rahbod.visit365;

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
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileUserActivity extends AppCompatActivity {

    DrawerLayout drawerProfileUser;
    private static final int time =1500;
    private static long BackPressed;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        setContentView(R.layout.navigationdraw_profile_user);

        drawerProfileUser = (DrawerLayout) findViewById(R.id.drawer_profile_user);

        NavigationView nvProfileUser = (NavigationView) findViewById(R.id.navigationViewProfileUser);
        nvProfileUser.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.credit_card_NavigationView:
                        Intent goTransactions = new Intent(ProfileUserActivity.this, TransactionActivity.class);
                        startActivity(goTransactions);
                        finish();
                        break;

                    case R.id.help_NavigationView:
                        Intent goHelp = new Intent(ProfileUserActivity.this, HelpActivity.class);
                        startActivity(goHelp);
                        finish();
                        break;

                    case R.id.user_NavigationView:
                        drawerProfileUser.closeDrawer(Gravity.LEFT);
                        break;

                    case R.id.abut_NavigationView:
                        Intent goAbout = new Intent(ProfileUserActivity.this, AboutActivity.class);
                        startActivity(goAbout);
                        finish();
                        break;

                    case R.id.home_NavigationView:
                        Intent goHome = new Intent(ProfileUserActivity.this, Index.class);
                        startActivity(goHome);
                        finish();
                        break;
                }

                return true;
            }
        });
    }

    public void openNvProfile(View view) {
        drawerProfileUser = (DrawerLayout) findViewById(R.id.drawer_profile_user);
        drawerProfileUser.openDrawer(Gravity.LEFT);
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
