package com.rahbod.visit365.Adapters;

import android.app.Activity;
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
import com.rahbod.visit365.R;
import com.rahbod.visit365.helper.SessionManager;
import com.rahbod.visit365.models.DateTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SelectTimeAdapter extends RecyclerView.Adapter<SelectTimeAdapter.SelectTimeViewHolder> {
    List<DateTime> datesList;
    AppCompatActivity context;

    public SelectTimeAdapter(List<DateTime> datesList, AppCompatActivity context) {
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
        holder.timeSelect.setText(datesList.get(position).getText());
        holder.timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject params = new JSONObject();
                    params.put("c", SessionManager.getExtrasPref(context).getInt("clinicId"));
                    params.put("d", SessionManager.getExtrasPref(context).getInt("doctorId"));
                    Log.e("sd", params.toString());
//                    AppController.getInstance().sendAuthRequest("api/userInfo", params, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                        }
//                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, datesList.get(position).hasAM()+"", Toast.LENGTH_LONG).show();
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
