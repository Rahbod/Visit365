package com.rahbod.visit365.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahbod.visit365.Font.ButtonFont;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.R;
import com.rahbod.visit365.models.Dates;

import java.util.List;

/**
 * Created by moien on 02/01/2018.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.dateViewHolder> {
    List<Dates> datesList;
    Context context;

    public DateAdapter(List<Dates> datesList, Context context) {
        this.datesList = datesList;
        this.context = context;
    }

    @Override
    public dateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step2_recycler,parent,false);
        return new dateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(dateViewHolder holder, int position) {
        holder.time.setText(datesList.get(position).getDate());
        holder.dataShow.setText(datesList.get(position).getDateShow());
    }

    @Override
    public int getItemCount() {
        return datesList.size();
    }

    public class dateViewHolder extends RecyclerView.ViewHolder {
        FontTextView time , dataShow;
        ButtonFont button;

        public dateViewHolder(View itemView) {
            super(itemView);
            time = (FontTextView) itemView.findViewById(R.id.dateVisitS2);
            button = (ButtonFont) itemView.findViewById(R.id.button4);
            dataShow = (FontTextView) itemView.findViewById(R.id.textView13);
        }
    }
    public void refreshList(List<Dates> datesList)
    {
        this.datesList.clear();
        this.datesList.addAll(datesList);
        this.notifyDataSetChanged();
    }
}
