package com.rahbod.visit365;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class Step3Fragment extends Fragment {


    public Step3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step3, container, false);

        HashMap<String, String> userInfo = SessionManager.getUserInfo(getActivity());


        String user = userInfo.get("firstName") + " " + userInfo.get("lastName");
        String nationalCode = userInfo.get("nationalCode");
        String mobile = userInfo.get("mobile");


        TextView txtUser = (TextView) rootView.findViewById(R.id.user);
        TextView txtNationalCode = (TextView) rootView.findViewById(R.id.nationalCode);
        TextView txtMobile = (TextView) rootView.findViewById(R.id.mobile);
        txtUser.setText(user);
        txtNationalCode.setText(nationalCode);
        txtMobile.setText(mobile);

//      BackToStep2
        ImageView btnBackToStep2 = (ImageView) rootView.findViewById(R.id.btnBackToStep2);
        btnBackToStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

    String visitID;
    Button btnGateway;

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        btnGateway = (Button) view.findViewById(R.id.btnGateway);

        final TextView txtClinicId = (TextView) view.findViewById(R.id.clinicId);
        final TextView txtDoctorId = (TextView) view.findViewById(R.id.doctorId);
        final TextView txtExpertiseId = (TextView) view.findViewById(R.id.expertiseId);
        final TextView txtDate = (TextView) view.findViewById(R.id.dateStep3);
        final TextView txtTime = (TextView) view.findViewById(R.id.timeStep3);
        final TextView txtcommission = (TextView) view.findViewById(R.id.commission);


        Map<String, ?> visitInfo = SessionManager.getExtrasPref(getContext()).getExtras();

        final String strClinicId = visitInfo.get("clinicId").toString();
        final String strDoctorId = visitInfo.get("doctorId").toString();
        final String strExpertise = visitInfo.get("expId").toString();
        final String strDate = visitInfo.get("date").toString();
        final String StrTime = visitInfo.get("time").toString();

        JSONObject params = new JSONObject();

        try {
            params.put("clinic_id", strClinicId);
            params.put("doctor_id", strDoctorId);
            params.put("expertise_id", strExpertise);
            params.put("date", strDate);
            params.put("time", StrTime);

            AppController.getInstance().sendAuthRequest("api/userInfo", params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("status")) {
                            final int commission = response.getInt("commission");

                            JSONObject visit = response.getJSONObject("visit");
                            visitID = visit.getString("visitID");
                            String clinicName = visit.getString("clinicName");
                            String doctorName = visit.getString("doctorName");
                            String doctorExpertise = visit.getString("doctorExpertise");
                            String date = visit.getString("date");
                            String doctorTime = visit.getString("doctorTime");

                            PersianDate pdate = new PersianDate(Long.parseLong(date) * 1000);
                            PersianDateFormat pdformater = new PersianDateFormat("j  F  13y");
                            String strData = pdformater.format(pdate);

                            txtClinicId.setText(clinicName);
                            txtDoctorId.setText(doctorName);
                            txtExpertiseId.setText(doctorExpertise);
                            txtDate.setText(strData);
                            txtTime.setText(doctorTime);

                            if (commission == 0) {
                                btnGateway.setText("تایید نهایی");
                                TextView txtCommission = (TextView) view.findViewById(R.id.txtcommission);
                                txtCommission.setText("جهت دریافت کد رهگیری لطفا اطلاعات فوق را تایید کنید.");
                                txtCommission.setLines(2);
                                txtCommission.setWidth(450);
                            } else
                                txtcommission.setText(commission + " تومان");

                            btnGateway.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    JSONObject params = new JSONObject();
                                    try {
                                        params.put("id", visitID);

                                        AppController.getInstance().sendAuthRequest("api/checkout", params, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    if (commission != 0) {
                                                        if (response.getBoolean("status")) {
                                                            String url = response.getString("url");
                                                            Intent intent = new Intent(Intent.ACTION_VIEW);
                                                            intent.setData(Uri.parse(url));
                                                            startActivity(intent);
                                                            new Handler().postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    getActivity().finish();
                                                                }
                                                            }, 10000);
                                                        }
                                                    } else {
                                                        Intent intent = new Intent(getActivity(), TrackingActivity.class);
                                                        intent.putExtra("id", visitID);
                                                        startActivity(intent);
                                                        getActivity().finish();
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
