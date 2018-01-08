package com.rahbod.visit365.Fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rahbod.visit365.Adapters.SelectTimeAdapter;
import com.rahbod.visit365.Font.FontBoldTextView;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.R;
import com.rahbod.visit365.models.Dates;

import java.util.ArrayList;
import java.util.List;


public class SelectTimeDialogFragment extends DialogFragment {
    List<Dates> datesList = new ArrayList<>();
    RecyclerView recyclerView;
    FontTextView date ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_select_time, container);

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.radius);

        recyclerView = (RecyclerView) view.findViewById(R.id.rec_select_time);
        Bundle bundle = getArguments();
        date = (FontTextView) view.findViewById(R.id.title_date);
        date.setText(bundle.getString("date"));
        String am = bundle.getString("am");
        String pm = bundle.getString("pm");
        if (!am.isEmpty() && !pm.isEmpty()) {
            datesList.add(new Dates(am));
            datesList.add(new Dates(pm));
        } else if (!am.isEmpty() && pm.isEmpty()) {
            datesList.add(new Dates(am));
        } else
            datesList.add(new Dates(pm));
        SelectTimeAdapter selectTimeAdapter = new SelectTimeAdapter(datesList, (AppCompatActivity) getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(selectTimeAdapter);

        return view;
    }
    @Override
    public void onStart()
    {
        super.onStart();

        if (getDialog() == null)
            return;

        getDialog().getWindow().setLayout(550,
                LinearLayout.LayoutParams.WRAP_CONTENT);

    }
}
