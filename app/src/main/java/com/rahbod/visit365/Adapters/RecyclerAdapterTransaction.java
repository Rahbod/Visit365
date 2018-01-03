package com.rahbod.visit365.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahbod.visit365.R;

import java.util.ArrayList;


public class RecyclerAdapterTransaction extends RecyclerView.Adapter<RecyclerAdapterTransaction.listViewHolder> {

    ArrayList<ListTrans> data;
    Context context;

    public RecyclerAdapterTransaction(ArrayList<ListTrans> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public listViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.transaction_rec, parent, false);

        return new listViewHolder(view);
    }

    @Override
    public void onBindViewHolder(listViewHolder holder, int position) {

        holder.txtAmount.setText(data.get(position).getAmount()+" تومان");
        holder.txtDate.setText(data.get(position).getDate());
        holder.txtGateway.setText(data.get(position).getGateway());
        holder.txtCode.setText("کد رهگیری: "+data.get(position).getCode());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class listViewHolder extends RecyclerView.ViewHolder{

        TextView txtAmount, txtDate, txtGateway, txtCode;

        public listViewHolder(View itemView) {
            super(itemView);

            txtAmount = (TextView) itemView.findViewById(R.id.AmountRec);
            txtDate = (TextView) itemView.findViewById(R.id.dataRec);
            txtGateway = (TextView) itemView.findViewById(R.id.gatewayRec);
            txtCode = (TextView) itemView.findViewById(R.id.codeRec);
        }
    }
}
