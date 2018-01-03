package com.rahbod.visit365.Adapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rahbod.visit365.Font.ButtonFont;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.R;
import com.rahbod.visit365.Step2Fragment;
import com.rahbod.visit365.models.DrList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterDrList extends RecyclerView.Adapter<AdapterDrList.DrListViewHolder> {

    private AppCompatActivity context;
    private List<DrList> drLists;

    public AdapterDrList(AppCompatActivity context, List<DrList> drLists) {
        this.context = context;
        this.drLists = drLists;
    }

    @Override
    public DrListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step_1_rec, parent, false);
        return new DrListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrListViewHolder holder, final int position) {
        //Picasso.with(context).load(drLists.get(position).getAvatar()).error(R.drawable.doctor).into(holder.imgAvatarDr);
        holder.txtTitleDr.setText(drLists.get(position).getName());
        holder.txtPresentDayDr.setText(drLists.get(position).getReserveDay());
        holder.btnProfileDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnReserveDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("doctorId",drLists.get(position).getDoctorId());
                bundle.putInt("clinicId",drLists.get(position).getClinicId());
                Log.d("moein",String.valueOf(drLists.get(position).getDoctorId()));
                Step2Fragment step2Fragment = new Step2Fragment();
                step2Fragment.setArguments(bundle);

                android.support.v4.app.FragmentManager fm = context.getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, step2Fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return drLists.size();
    }

    public class DrListViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgAvatarDr;
        private FontTextView txtTitleDr;
        private FontTextView txtPresentDayDr;
        private ButtonFont btnProfileDr;
        private ButtonFont btnReserveDr;

        public DrListViewHolder(View itemView) {
            super(itemView);
            imgAvatarDr = (CircleImageView) itemView.findViewById(R.id.imageView2);
            txtTitleDr = (FontTextView) itemView.findViewById(R.id.textView6);
            txtPresentDayDr = (FontTextView) itemView.findViewById(R.id.textView7);
            btnProfileDr = (ButtonFont) itemView.findViewById(R.id.button);
            btnReserveDr = (ButtonFont) itemView.findViewById(R.id.button2);
        }
    }
}