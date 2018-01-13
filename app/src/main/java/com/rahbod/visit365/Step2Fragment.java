package com.rahbod.visit365;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.Adapters.DateAdapter;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.helper.SessionManager;
import com.rahbod.visit365.models.Dates;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class Step2Fragment extends Fragment {
    long miliFrom, miliTo, miliNow;
    CircleImageView avatarDoctor;
    FontTextView doctorTitle, clinicTitle, clinicPhone, tvFromDate, tvToDate;
    RecyclerView recyclerView;
    JSONObject jsonObject, objectDoctor, objectClinic, params;
    JSONArray jsonArray;
    List<Dates> dates = new ArrayList<>();
    DateAdapter dateAdapter = null;
    PersianDate persianDate;
    PersianDateFormat persianDateFormat;
    String dateShow, am, pm;
    boolean detailsFilled = false;

    int doctorId;
    int clinicId;
    int expId;

    public Step2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step2, container, false);

        ImageView btnBackToStep1 = (ImageView) rootView.findViewById(R.id.btnBackToStep1);
        btnBackToStep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Step1Activity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // save doctor id and clinic id to session
        SessionManager extras = SessionManager.getExtrasPref(getContext());
        doctorId = extras.getInt("doctorId");
        clinicId= extras.getInt("clinicId");
        expId= extras.getInt("expId");

        params = new JSONObject();
        miliNow = System.currentTimeMillis() / 1000;
        recyclerView = (RecyclerView) view.findViewById(R.id.rec_present_day);
        doctorTitle = (FontTextView) view.findViewById(R.id.drNameProfile);
        clinicPhone = (FontTextView) view.findViewById(R.id.drPhoneProfile);
        clinicTitle = (FontTextView) view.findViewById(R.id.tvClinicTitle);
        tvFromDate = (FontTextView) view.findViewById(R.id.tv_from);
        tvToDate = (FontTextView) view.findViewById(R.id.tv_to);
        avatarDoctor = (CircleImageView) view.findViewById(R.id.drAvatarProfile);

        try {
            params.put("doctor_id", doctorId);
            params.put("clinic_id", clinicId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // send choose request
        ChooseDateRequest(params);

        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        miliFrom = persianCalendar.getTimeInMillis() / 1000;
                        if (miliFrom >= miliNow)
                            tvFromDate.setText(persianCalendar.getPersianYear() + "-" + persianCalendar.getPersianMonthName() + "-" + persianCalendar.getPersianDay());
                        else {
                            tvToDate.setText("");
                            tvFromDate.setText("");
                            Toast.makeText(getActivity(), "تاریخ مورد نظر منقضی شده است.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onDisimised() {

                    }
                });
            }
        });
        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        miliTo = persianCalendar.getTimeInMillis() / 1000;
                        dates = new ArrayList<>();
                        if (!(tvFromDate.length() == 0)) {
                            if (miliTo >= miliFrom && miliTo >= miliNow) {
                                tvToDate.setText(persianCalendar.getPersianDay() + " " + persianCalendar.getPersianMonthName() + " " + persianCalendar.getPersianYear());
                                try {
                                    params.put("from", miliFrom);
                                    params.put("to", miliTo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // send choose request
                                ChooseDateRequest(params);

                            } else {
                                tvToDate.setText("");
                                Toast.makeText(getActivity(), "تاریخ مورد نظر باید از تاریخ شروع بزرگتر باشد.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "ابتدا تاریخ شروع را وارد کنید", Toast.LENGTH_SHORT).show();
                            tvToDate.setText("");
                        }
                    }

                    @Override
                    public void onDisimised() {
                    }
                });
            }
        });
    }

    private void showCalender(Listener listener) {
        PersianDatePickerDialog picker2 = new PersianDatePickerDialog(getActivity())
                .setPositiveButtonString("تایید")
                .setNegativeButton("لغو")
                .setTodayButtonVisible(true)
                .setTodayButton("امروز")
                .setActionTextColor(Color.GRAY)
                .setListener(listener);
        picker2.show();
    }

    private void ChooseDateRequest(JSONObject params) {
        AppController.getInstance().sendRequest("api/getDates", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("status")) {

                        // fill doctor and clinic details
                        if (!detailsFilled) {
                            objectDoctor = new JSONObject();
                            objectDoctor = response.getJSONObject("doctor");
                            doctorTitle.setText(objectDoctor.getString("name"));
                            String doctorAvatar = objectDoctor.getString("avatar");
                            if (!doctorAvatar.isEmpty()) {
                                Picasso.with(getActivity()).load(doctorAvatar).into(avatarDoctor);
                            }

                            objectClinic = response.getJSONObject("clinic");
                            String clinicName = objectClinic.getString("name");
                            clinicTitle.setText(clinicName);
                            String phoneClinic = objectClinic.getString("phone");
                            clinicPhone.setText(phoneClinic);

                            // set default date values
                            miliFrom = Long.parseLong(response.getString("from"));
                            PersianDate persianDateFrom = new PersianDate(miliFrom * 1000);
                            PersianDateFormat persianDateFormatFrom = new PersianDateFormat("j F 13y");
                            String defaultFrom = persianDateFormatFrom.format(persianDateFrom);
                            tvFromDate.setText(defaultFrom);

                            miliTo = Long.parseLong(response.getString("to"));
                            PersianDate persianDateTo = new PersianDate(miliTo * 1000);
                            PersianDateFormat persianDateFormatTo = new PersianDateFormat("j F 13y");
                            String defaultTo = persianDateFormatTo.format(persianDateTo);
                            tvToDate.setText(defaultTo);

                            detailsFilled = true;
                        }
                        //

                        //
                        jsonArray = response.getJSONArray("days");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                jsonObject = jsonArray.getJSONObject(i);
                                Long unixTime = Long.parseLong(jsonObject.getString("date"));
                                persianDate = new PersianDate(unixTime * 1000);
                                persianDateFormat = new PersianDateFormat("j F 13y");
                                dateShow = jsonObject.getString("dateShow");
                                am = "";
                                pm = "";
                                if (jsonObject.has("AM")) {
                                    am = jsonObject.getString("AM");
                                }
                                if (jsonObject.has("PM")) {
                                    pm = jsonObject.getString("PM");
                                }
                                String str = persianDateFormat.format(persianDate);
                                dates.add(new Dates(unixTime, str, dateShow, am, pm));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (dateAdapter == null) {
                            dateAdapter = new DateAdapter(dates, (AppCompatActivity) getActivity());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(dateAdapter);
                        } else {
                            dateAdapter = new DateAdapter(dates, (AppCompatActivity) getActivity());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(dateAdapter);
                            dateAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}