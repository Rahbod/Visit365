package com.rahbod.visit365.Adapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahbod.visit365.Font.ButtonFont;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.Fragment.SelectTimeDialogFragment;
import com.rahbod.visit365.R;
import com.rahbod.visit365.models.Dates;

import java.util.List;


public class DateAdapter extends RecyclerView.Adapter<DateAdapter.dateViewHolder> {
    List<Dates> datesList;
    AppCompatActivity context;

    public DateAdapter(List<Dates> datesList, AppCompatActivity context) {
        this.datesList = datesList;
        this.context = context;
    }

    @Override
    public dateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step2_recycler,parent,false);
        return new dateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(dateViewHolder holder, final int position) {
        holder.time.setText(datesList.get(position).getDate());
        holder.dataShow.setText(datesList.get(position).getDateShow());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("pm",datesList.get(position).getPm());
                bundle.putString("am",datesList.get(position).getAm());
                bundle.putString("date", datesList.get(position).getDate());

                SelectTimeDialogFragment selectTimeDialogFragment = new SelectTimeDialogFragment();
                selectTimeDialogFragment.setCancelable(true);
                selectTimeDialogFragment.setArguments(bundle);
                selectTimeDialogFragment.show(context.getSupportFragmentManager(), "stdf");
            }
        });
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

}
