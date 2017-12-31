package com.rahbod.visit365;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Step1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1Activity);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String a = bundle.getString("Id");
            Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
        }
    }
}
