package com.rahbod.visit365.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rahbod.visit365.AppController;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.R;
import com.rahbod.visit365.models.Dates;

import java.util.List;

public class SelectTimeAdapter extends RecyclerView.Adapter<SelectTimeAdapter.SelectTimeViewHolder> {
    List<Dates> datesList;
    AppCompatActivity context;

    public SelectTimeAdapter(List<Dates> datesList, AppCompatActivity context) {
        this.datesList = datesList;
        this.context = context;
    }

    @Override
    public SelectTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_select_time, parent, false);
        return new SelectTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectTimeViewHolder holder, final int position) {
        holder.timeSelect.setText(datesList.get(position).getPm());
        holder.timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
