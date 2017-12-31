package com.rahbod.visit365;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.rahbod.visit365.Fragment.Select_Dr;
import com.rahbod.visit365.helper.AccessTokenHelper;
import com.rahbod.visit365.helper.SessionManager;

import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Index extends AppCompatActivity {
    String accessToken;
    Button btnLogout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

//        btnLogout = (Button) findViewById(R.id.btnLogout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Index.this, "Tis", Toast.LENGTH_SHORT).show();
////                if (AccessTokenHelper.logout())
////                    restart();
//            }
//        });

//        SessionManager sessionManager = new SessionManager(getApplicationContext());
//        sessionManager.clear();

        Select_Dr select_dr = new Select_Dr();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home, select_dr);
        transaction.commit();
    }

    public void openNv(View view){
        AccessTokenHelper.logout();
        restart();
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLy);
//        drawerLayout.openDrawer(Gravity.LEFT);
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
