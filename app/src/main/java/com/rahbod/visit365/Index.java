package com.rahbod.visit365;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Index extends AppCompatActivity {

    RecyclerAdapter adapter;
    RecyclerView listExp;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


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

    public void openNv(View view){

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLy);
        drawerLayout.openDrawer(Gravity.LEFT);

    }
}
