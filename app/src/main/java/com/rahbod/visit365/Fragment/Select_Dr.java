package com.rahbod.visit365.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahbod.visit365.R;
import com.rahbod.visit365.RecyclerAdapter;

import java.util.ArrayList;


public class Select_Dr extends Fragment {

    RecyclerAdapter adapter;
    RecyclerView listExp;

    public Select_Dr() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select_dr_fragment, container, false);

        ArrayList<String[]> expertises = new ArrayList<String[]>();
        expertises.add(new String[]{"1", "قلب و عروق", "heart_index"});
        expertises.add(new String[]{"2", "مغز و اعصاب", "brain_index"});
        expertises.add(new String[]{"3", "چشم پزشکی", "eye_index"});
        expertises.add(new String[]{"4", "پوست و مو", "hair_index"});
        expertises.add(new String[]{"5", "دندان پزشکی", "dental_index"});
        expertises.add(new String[]{"6", "گوارش و کبد", "digestion_index"});
        expertises.add(new String[]{"7", "گوش و حلق و بینی", "listen_index"});
        expertises.add(new String[]{"8", "ریه", "lung_index"});
        expertises.add(new String[]{"9", "زنان و زایمان", "givingbirth_index"});
        expertises.add(new String[]{"10", "ارتوپدی", "orthopedic_index"});
        expertises.add(new String[]{"11", "روماتولوژی", "rheumatology_index"});
        expertises.add(new String[]{"12", "ارولوژی", "urology_index"});


        adapter = new RecyclerAdapter(expertises, getActivity());

        listExp = (RecyclerView) rootView.findViewById(R.id.recSelectExp);

        listExp.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        listExp.setAdapter(adapter);


        return rootView;
    }
}
