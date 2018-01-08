package com.rahbod.visit365;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.rahbod.visit365.Adapters.AdapterVisitList;
import com.rahbod.visit365.Adapters.RecyclerAdapter;
import com.rahbod.visit365.models.VisList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class VisitList extends AppCompatActivity {

    ArrayList<VisList> data;
    RecyclerView recVisList;
    AdapterVisitList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_list);

        AppController.getInstance().sendAuthRequest("api/visits", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) {

                        JSONArray visits = response.getJSONArray("visits");

                        data = new ArrayList<>();

                        for (int i = 0; i < visits.length(); i++) {
                            JSONObject visitsList = visits.getJSONObject(i);

                            String objClinic = visitsList.getString("clinic");
                            String objDoctor = visitsList.getString("doctor");
                            String objStatus = visitsList.getString("status");
                            String objCreateDate = visitsList.getString("createDate");
                            String objDate = visitsList.getString("date");
                            String objVisitDate = visitsList.getString("visitDate");
                            String objTrackingCode = visitsList.getString("trackingCode");

                            PersianDate perDate = new PersianDate(Long.parseLong(objDate) * 1000);
                            PersianDateFormat perDateFormat = new PersianDateFormat("j  F  13y");
                            String strData = perDateFormat.format(perDate);

                            PersianDate perCreateDate = new PersianDate(Long.parseLong(objCreateDate) * 1000);
                            PersianDateFormat perCreateDateFormat = new PersianDateFormat("j  F  13y");
                            String strCreateData = perCreateDateFormat.format(perCreateDate);

                            String strVisitData;
                            if (!objVisitDate.isEmpty()) {
                                PersianDate perVisitDate = new PersianDate(Long.parseLong(objVisitDate) * 1000);
                                PersianDateFormat perVisitDateFormat = new PersianDateFormat("j  F  13y");
                                strVisitData = perVisitDateFormat.format(perVisitDate);
                            }
                            else
                                strVisitData = "---";


                            data.add(new VisList(objClinic, objDoctor, strCreateData, strData, strVisitData, objTrackingCode, objStatus));

                            adapter = new AdapterVisitList(data, VisitList.this);

                            recVisList = (RecyclerView) findViewById(R.id.recVisitsList);
                            recVisList.setLayoutManager(new LinearLayoutManager(VisitList.this));
                            recVisList.setAdapter(adapter);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
