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

        holder.txtClinic.setText(data.get(position).getClinic());
        holder.txtDoctor.setText(data.get(position).getDoctor());
        holder.txtStatus.setText(data.get(position).getStatus());
        holder.txtCreateDate.setText(data.get(position).getCreateDate());
        holder.txtDate.setText(data.get(position).getDate());
        holder.txtVisitDate.setText(data.get(position).getVisitDate());
        holder.txtTrackingCode.setText(data.get(position).getTrackingCode());

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

            txtClinic = (TextView) itemView.findViewById(R.id.txtClinicRecList);
            txtDoctor = (TextView) itemView.findViewById(R.id.txtDrRecList);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatusDate);
            txtCreateDate = (TextView) itemView.findViewById(R.id.txtCreateDate);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateRecList);
            txtVisitDate = (TextView) itemView.findViewById(R.id.txtVisitDate);
            txtTrackingCode = (TextView) itemView.findViewById(R.id.txtTrackingCode);

        }
    }
}
