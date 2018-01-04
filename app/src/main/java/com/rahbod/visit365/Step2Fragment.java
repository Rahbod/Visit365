package com.rahbod.visit365;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.rahbod.visit365.Adapters.DateAdapter;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.models.Dates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class Step2Fragment extends Fragment {
    FontTextView doctorTitle, clinicTitle , doctorPhone , tvFromDate , tvToDate;
    RecyclerView recyclerView;
    JSONObject jsonObject;
    List<Dates> dates = new ArrayList<>();
    DateAdapter dateAdapter;
    PersianDate persianDate;
    PersianDateFormat persianDateFormat;
    String from , to;
    private static final String TAG = "Tag";

    public Step2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        JSONObject params = new JSONObject();
        recyclerView = (RecyclerView) view.findViewById(R.id.rec_present_day);
        doctorTitle = (FontTextView) view.findViewById(R.id.drNameProfile);
        doctorPhone = (FontTextView) view.findViewById(R.id.drPhoneProfile);
        clinicTitle = (FontTextView) view.findViewById(R.id.tvClinicTitle);
        tvFromDate = (FontTextView) view.findViewById(R.id.tv_from);
        tvToDate = (FontTextView) view.findViewById(R.id.tv_to);

        try {
            params.put("doctor_id", bundle.getInt("doctorId"));
            params.put("clinic_id", bundle.getInt("clinicId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AppController.getInstance().sendRequest("api/getDates", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) {
                        JSONArray jsonArray = response.getJSONArray("days");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            dates.add(new Dates(jsonObject.getString("date")));
                        }
                        dateAdapter = new DateAdapter(dates, getActivity());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(dateAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        persianDate = new PersianDate();
        persianDateFormat = new PersianDateFormat();
        persianDateFormat.format(persianDate);


        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showCalender(tvFromDate);
            }
        });
        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(tvToDate);
            }
        });
    }
    private void showCalender(final FontTextView tv)
    {
        PersianDatePickerDialog picker2 = new PersianDatePickerDialog(getActivity())
                .setPositiveButtonString("تایید")
                .setNegativeButton("لغو")
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        tv.setText(persianCalendar.getPersianYear() + "-" + persianCalendar.getPersianMonth() + "-" + persianCalendar.getPersianDay());
                    }

                    @Override
                    public void onDisimised() {
                    }
                });
        picker2.show();
    }
}