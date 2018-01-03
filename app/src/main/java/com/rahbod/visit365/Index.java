package com.rahbod.visit365;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.rahbod.visit365.Adapters.RecyclerAdapter;
import com.rahbod.visit365.helper.AccessTokenHelper;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Index extends AppCompatActivity {

    RecyclerAdapter adapter;
    RecyclerView listExp;
    String accessToken;
    Button btnLogout;
    DrawerLayout drawerLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        setContentView(R.layout.navigationdraw);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_index);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationViewIndex);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.credit_card_NavigationView:
                        Intent goTransactions = new Intent(Index.this, TransactionActivity.class);
                        startActivity(goTransactions);
                        break;

                    case R.id.help_NavigationView:
                        Intent goHelp = new Intent(Index.this, HelpActivity.class);
                        startActivity(goHelp);
                        break;

                    case R.id.user_NavigationView:
                        Intent goProfile = new Intent(Index.this, ProfileUserActivity.class);
                        startActivity(goProfile);
                        break;

                    case R.id.abut_NavigationView:
                        Intent goAbout = new Intent(Index.this, AboutActivity.class);
                        startActivity(goAbout);
                        break;

                    case R.id.home_NavigationView:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;



                }

                return true;
            }
        });


        ArrayList<String[]> expertises = new ArrayList<String[]>();
        expertises.add(new String[]{"1", "قلب و عروق", "heart_index"});
        expertises.add(new String[]{"2", "گوش و حلق و بینی", "listen_index"});
        expertises.add(new String[]{"3", "چشم پزشکی", "eye_index"});
        expertises.add(new String[]{"4", "مغز و اعصاب", "brain_index"});
        expertises.add(new String[]{"5", "پوست و مو", "hair_index"});
        expertises.add(new String[]{"6", "گوارش و کبد", "digestion_index"});
        expertises.add(new String[]{"7", "دندان پزشکی", "dental_index"});
        expertises.add(new String[]{"8", "ارتوپدی", "orthopedic_index"});
        expertises.add(new String[]{"9", "ریه", "lung_index"});
        expertises.add(new String[]{"10", "ارولوژی", "urology_index"});
        expertises.add(new String[]{"11", "زنان و زایمان", "givingbirth_index"});
        expertises.add(new String[]{"12", "روماتولوژی", "rheumatology_index"});

        adapter = new RecyclerAdapter(expertises, this);

        listExp = (RecyclerView) findViewById(R.id.recSelectExp);

        listExp.setLayoutManager(new GridLayoutManager(this, 2));

        listExp.setAdapter(adapter);
    }

    public void openNv(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
        drawerLayout.findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
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
}