package com.rahbod.visit365;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class Step2Fragment extends Fragment {


    private static final String TAG ="Tag" ;

    public Step2Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        Toast.makeText(this.getContext(),bundle.getInt(""), Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_step2, container, false);
    }

}
