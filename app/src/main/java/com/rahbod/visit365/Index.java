package com.rahbod.visit365;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;


import com.rahbod.visit365.Fragment.Select_Dr;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Index extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_step4);

        Select_Dr select_dr = new Select_Dr();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_home, select_dr);
        transaction.commit();

        
    }

    public void openNv(View view){

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLy);
        drawerLayout.openDrawer(Gravity.LEFT);

    }
}
