package com.rahbod.visit365;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.listViewHolder>{

    List<String[]> data;
    Context context;

    public RecyclerAdapter(List<String[]> data,  Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public listViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_expertise, parent, false);
        return new listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(listViewHolder holder, final int position) {

        holder.txtName.setText(data.get(position)[1]);
        holder.imgIcon.setImageResource(context.getResources().getIdentifier(data.get(position)[2], "drawable", context.getPackageName()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Step1Activity.class);
                intent.putExtra("Id", data.get(position)[0]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class listViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        ImageView imgIcon;

        public listViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imgIcon = (ImageView) itemView.findViewById(R.id.exp_img);

        }
    }
}
