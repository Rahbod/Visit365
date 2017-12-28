package com.rahbod.visit365.Fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rahbod.visit365.R;


public class Select_Dr extends Fragment {


    public Select_Dr() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select_dr_fragment, container, false);

        ImageView img_brainIndex = (ImageView) rootView.findViewById(R.id.img_brainIndex);

        img_brainIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Neurology_Fragment neurology_fragment = new Neurology_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_home, neurology_fragment);
                transaction.commit();

            }
        });
        return rootView;
    }
}
