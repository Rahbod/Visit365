package com.rahbod.visit365;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.Adapters.AdapterDrList;
import com.rahbod.visit365.models.DrList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Step1Activity extends AppCompatActivity {
    JSONObject params, doctor;
    AdapterDrList adapterDrList;
    RecyclerView recyclerView;
    List<DrList> DrList = new ArrayList<>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step1activity);
        recyclerView = (RecyclerView) findViewById(R.id.rec_step1);
        params = new JSONObject();
        Bundle bundle = getIntent().getExtras();

        TextView txtExp = (TextView) findViewById(R.id.drExp);
        String strExp = bundle.getString("ExpDr");
        txtExp.setText("پزشکان "+strExp);

        if (bundle != null) {
            try {
                params.put("id", bundle.getString("Id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppController.getInstance().sendRequest("api/getDoctors", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            Log.d("TAG", "onResponse: " + response);
                            JSONArray json = response.getJSONArray("doctors");
                            for (int i = 0; i < json.length(); i++) {
                                doctor = json.getJSONObject(i);
                                DrList.add(new DrList(doctor.getString("name"), doctor.getString("avatar"), doctor.getInt("doctorID"), doctor.getInt("clinicID"), doctor.getString("days")));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapterDrList = new AdapterDrList(Step1Activity.this, DrList);
                            recyclerView.setAdapter(adapterDrList);
                        } else {
                            Toast.makeText(Step1Activity.this, "پزشکی برای این تخصص ثبت نشده است", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    public void openIndex(View view) {
        Intent intent = new Intent(this, Index.class);
        startActivity(intent);
        finish();
    }
}