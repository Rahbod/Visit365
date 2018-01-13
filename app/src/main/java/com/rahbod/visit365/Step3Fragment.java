package com.rahbod.visit365;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahbod.visit365.helper.SessionManager;


public class Step3Fragment extends Fragment {


    public Step3Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("sd", SessionManager.getExtrasPref(getContext()).getExtras().toString());

        return inflater.inflate(R.layout.fragment_step3, container, false);
    }

}
