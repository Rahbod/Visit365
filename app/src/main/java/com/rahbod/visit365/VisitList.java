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
                    if (response.getBoolean("status")){

                        JSONArray visits = response.getJSONArray("visits");

                        data = new ArrayList<>();

                        for (int i=0; i < visits.length(); i++){
                            JSONObject visitsList = visits.getJSONObject(i);

                            String clinic = visitsList.getString("clinic");
                            String doctor = visitsList.getString("doctor");
                            String status = visitsList.getString("status");
                            String createDate = visitsList.getString("createDate");
                            String date = visitsList.getString("date");
                            String visitDate = visitsList.getString("visitDate");
                            String trackingCode = visitsList.getString("trackingCode");

                            PersianDate pdate = new PersianDate(Long.parseLong(date) * 1000);
                            PersianDateFormat pdformater = new PersianDateFormat("j  F  13y");
                            String strData = pdformater.format(pdate);

                            PersianDate pDate = new PersianDate(Long.parseLong(createDate) * 1000);
                            PersianDateFormat pdFormater = new PersianDateFormat("j  F  13y");
                            String strData2 = pdFormater.format(pDate);

                            PersianDate pVisitData = new PersianDate(Long.parseLong(trackingCode) * 1000);
                            PersianDateFormat Formater = new PersianDateFormat("j  F  13y");
                            String strData3 = Formater.format(pVisitData);

                            data.add(new VisList(clinic, doctor, status, strData2, strData, strData3, "کد رهگیری: "+trackingCode));

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
