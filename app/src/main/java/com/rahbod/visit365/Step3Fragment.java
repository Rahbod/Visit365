package com.rahbod.visit365;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahbod.visit365.helper.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Step3Fragment extends Fragment {


    public Step3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_step3, container, false);

        HashMap<String, String> userInfo = SessionManager.getUserInfo(getActivity());
        Map<String, ?> visitInfo = SessionManager.getExtrasPref(getContext()).getExtras();
        Log.e("sd", visitInfo.get("doctorId").toString());
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
                Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
