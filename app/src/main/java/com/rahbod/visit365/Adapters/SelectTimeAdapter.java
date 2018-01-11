package com.rahbod.visit365.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.rahbod.visit365.AppController;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.Fragment.SelectTimeDialogFragment;
import com.rahbod.visit365.R;
import com.rahbod.visit365.Step2Fragment;
import com.rahbod.visit365.Step3Fragment;
import com.rahbod.visit365.helper.SessionManager;
import com.rahbod.visit365.models.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SelectTimeAdapter extends RecyclerView.Adapter<SelectTimeAdapter.SelectTimeViewHolder> {
    List<DateTime> datesList;
    AppCompatActivity context;
    SelectTimeDialogFragment dialog;

    public SelectTimeAdapter(List<DateTime> datesList, AppCompatActivity context, SelectTimeDialogFragment dialog) {
        this.datesList = datesList;
        this.context = context;
        this.dialog = dialog;
    }

    @Override
    public SelectTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_select_time, parent, false);
        return new SelectTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectTimeViewHolder holder, final int position) {
        holder.timeSelect.setText(datesList.get(position).getText());
        holder.timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getExtrasPref(context).putExtra("time", datesList.get(position).hasAM()?"am":"pm");
                Step3Fragment step3Fragment = new Step3Fragment();
                android.support.v4.app.FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, step3Fragment).addToBackStack(null).commit();
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datesList.size();
    }

    public class SelectTimeViewHolder extends RecyclerView.ViewHolder {
        FontTextView timeSelect;

        public SelectTimeViewHolder(View itemView) {
            super(itemView);
            timeSelect = (FontTextView) itemView.findViewById(R.id.time_select);
        }
    }
}
