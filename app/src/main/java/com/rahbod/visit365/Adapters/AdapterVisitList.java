package com.rahbod.visit365.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import com.rahbod.visit365.R;
import com.rahbod.visit365.models.VisList;

import java.util.ArrayList;

/**
 * Created by Behnam on 1/7/2018.
 */

public class AdapterVisitList extends RecyclerView.Adapter<AdapterVisitList.listViewHolder> {

    ArrayList<VisList> data;
    Context context;

    public AdapterVisitList(ArrayList<VisList> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public listViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.visits_rec, parent, false);

        return new listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(listViewHolder holder, int position) {

        holder.txtClinic.setText(data.get(position).getStrClinic());
        holder.txtDoctor.setText(data.get(position).getStrDoctor());
        holder.txtStatus.setText("وضعیت: "+data.get(position).getStrStatus());
        holder.txtCreateDate.setText("تاریخ ثبت: "+data.get(position).getStrCreateDate());
        holder.txtDate.setText("تاریخ مراجعه: "+data.get(position).getStrDate());
        holder.txtVisitDate.setText("تاریخ ویزیت: "+data.get(position).getStrVisitDate());
        holder.txtTrackingCode.setText("کد رهگیری: "+data.get(position).getStrTrackingCode());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class listViewHolder extends RecyclerView.ViewHolder{

        TextView txtClinic;
        TextView txtDoctor;
        TextView txtStatus;
        TextView txtCreateDate;
        TextView txtDate;
        TextView txtVisitDate;
        TextView txtTrackingCode;

        public listViewHolder(View itemView) {
            super(itemView);

            txtClinic = (TextView) itemView.findViewById(R.id.txtClinic);
            txtDoctor = (TextView) itemView.findViewById(R.id.txtDr);
            txtTrackingCode = (TextView) itemView.findViewById(R.id.txtTrackingCode);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtVisitDate = (TextView) itemView.findViewById(R.id.txtVisitDate);
            txtCreateDate = (TextView) itemView.findViewById(R.id.txtCreateDate);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatusDate);




        }
    }
}
