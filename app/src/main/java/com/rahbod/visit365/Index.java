package com.rahbod.visit365;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import  android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.rahbod.visit365.Adapters.RecyclerAdapter;
import com.rahbod.visit365.Fragment.UserInfoDialogFragment;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.rahbod.visit365.helper.SessionManager;
import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Index extends AppCompatActivity implements UserInfoDialogFragment.dialogDoneListener{
    TextView tvUserName, tvMobile;
    RecyclerAdapter adapter;
    RecyclerView listExp;
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
        setContentView(R.layout.activity_index);
        setContentView(R.layout.navigationdraw_index);

            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


//         show dialog fragment
        if(!SessionManager.validUserInfo(this)){
            UserInfoDialogFragment udf = new UserInfoDialogFragment();
            udf.setCancelable(false);
            udf.show(getSupportFragmentManager(),"UserInfoDialog");
        }

        // clear session extras
        SessionManager.getExtrasPref(this).clearExtras();

        NavigationView navigationView = (NavigationView) findViewById(R.id.btnNav);

        View header =navigationView.getHeaderView(0);
        tvUserName = (TextView) header.findViewById(R.id.tvUserName);
        tvMobile = (TextView) header.findViewById(R.id.tvMobile);

        if(!SessionManager.getUserInfo(this).get("firstName").isEmpty() && !SessionManager.getUserInfo(this).get("lastName").isEmpty()) {
            String NameFamily = SessionManager.getUserInfo(this).get("firstName") + " " + SessionManager.getUserInfo(this).get("lastName");
            tvUserName.setText(NameFamily);
        }

        String Mobile = SessionManager.getUserInfo(this).get("mobile");

        if(!Mobile.isEmpty())
            tvMobile.setText(Mobile);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_index);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
                    case R.id.visit_NavigationView:
                        Intent goVisits = new Intent(Index.this, VisitList.class);
                        startActivity(goVisits);
                        break;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }, 250);
                return true;
            }
        });


        ArrayList<String[]> expertises = new ArrayList<>();
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

        adapter = new RecyclerAdapter(expertises, Index.this);

        listExp = (RecyclerView) findViewById(R.id.recSelectExp);
        listExp.setLayoutManager(new GridLayoutManager(this, 2));
        listExp.setAdapter(adapter);
    }

    public void openNvIndex(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
        drawerLayout.findViewById(R.id.btnExitIndex).setOnClickListener(new View.OnClickListener() {
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
        drawerLayout.closeDrawer(Gravity.LEFT);
        if (time + BackPressed>System.currentTimeMillis()){
            super.onBackPressed();
        }
        else
            Toast.makeText(this, "لطفا کلید برگشت را مجددا فشار دهید.", Toast.LENGTH_SHORT).show();

        BackPressed = System.currentTimeMillis();
    }

    @Override
    public void onDone(boolean state) {
        if(!SessionManager.getUserInfo(this).get("firstName").isEmpty() && !SessionManager.getUserInfo(this).get("lastName").isEmpty()) {
            String NameFamily = SessionManager.getUserInfo(this).get("firstName") + " " + SessionManager.getUserInfo(this).get("lastName");
            tvUserName.setText(NameFamily);
        }

        String Mobile = SessionManager.getUserInfo(this).get("mobile");

        if(!Mobile.isEmpty())
            tvMobile.setText(Mobile);
    }
}