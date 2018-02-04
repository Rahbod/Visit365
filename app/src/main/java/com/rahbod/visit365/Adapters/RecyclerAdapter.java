package com.rahbod.visit365.Adapters;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahbod.visit365.Index;
import com.rahbod.visit365.R;
import com.rahbod.visit365.Step1Activity;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.listViewHolder> {

    List<String[]> data;
    AppCompatActivity context;

    public RecyclerAdapter(List<String[]> data, AppCompatActivity context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public listViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_expertise, parent, false);
        return new listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final listViewHolder holder, final int position) {

        holder.txtName.setText(data.get(position)[1]);
        holder.imgIcon.setImageResource(context.getResources().getIdentifier(data.get(position)[2], "drawable", context.getPackageName()));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = context.getResources().getDisplayMetrics().density;
        //float height = displayMetrics.heightPixels / density;
        float width = displayMetrics.widthPixels / density;

        float padding = (((width / 2) - 100) / 2) * density;
        Log.e("masoud", padding + "");
        holder.imgIcon.setPadding((int) padding, 0, (int) padding, 30);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 24) {
                    Intent intent = new Intent(context, Step1Activity.class);
                    Pair[] pair = new Pair[1];
                    pair[0] = new Pair<View, String>(holder.imgIcon, "ImageTrans");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, pair);
                    intent.putExtra("Id", data.get(position)[0]);
                    intent.putExtra("ExpDr", data.get(position)[1]);
                    context.startActivity(intent, options.toBundle());
                } else {
                    Intent intent = new Intent(context, Step1Activity.class);
                    intent.putExtra("Id", data.get(position)[0]);
                    intent.putExtra("ExpDr", data.get(position)[1]);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class listViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        ImageView imgIcon;

        public listViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            imgIcon = (ImageView) itemView.findViewById(R.id.exp_img);

        }
    }
}
