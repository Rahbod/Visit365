package com.rahbod.visit365.Adapters;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahbod.visit365.Font.ButtonFont;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.ProfileActivity;
import com.rahbod.visit365.R;
import com.rahbod.visit365.Step2Fragment;
import com.rahbod.visit365.helper.SessionManager;
import com.rahbod.visit365.models.DrList;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterDrList extends RecyclerView.Adapter<AdapterDrList.DrListViewHolder> {

    private AppCompatActivity context;
    private List<DrList> drLists;
    private int doctorId;
    private int clinicId;

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
    public void onBindViewHolder(final DrListViewHolder holder, int position) {
        doctorId = drLists.get(position).getDoctorId();
        clinicId = drLists.get(position).getClinicId();

        if (!drLists.get(position).getAvatar().isEmpty())
            Picasso.with(context).load(drLists.get(position).getAvatar()).error(R.drawable.profile).into(holder.imgAvatarDr);
        else
            holder.imgAvatarDr.setImageResource(R.drawable.profile);

        holder.txtTitleDr.setText(drLists.get(position).getName());
        holder.txtPresentDayDr.setText(drLists.get(position).getReserveDay());
        holder.btnReserveDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // save doctor id and clinic id to session
                    SessionManager extras = SessionManager.getExtrasPref(context);
                    extras.putExtra("doctorId", doctorId);
                    extras.putExtra("clinicId", clinicId);

                    Step2Fragment step2Fragment = new Step2Fragment();
                    FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
                    transaction.add(R.id.fragment_container, step2Fragment).addToBackStack(null).commit();

            }
        });
        holder.btnProfileDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 24) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    Pair[] pair = new Pair[2];
                    pair[0] = new Pair<View, String>(holder.imgAvatarDr, "ImageProfileDr");
                    pair[1] = new Pair<View, String>(holder.txtTitleDr, "NameProfileDr");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, pair);
                    intent.putExtra("doctorId", doctorId);
                    intent.putExtra("clinicId", clinicId);
                    Log.e("doctorId= ", doctorId + "");
                    context.startActivity(intent, options.toBundle());
                } else {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("doctorId", doctorId);
                    intent.putExtra("clinicId", clinicId);
                    Log.e("doctorId= ", doctorId + "");
                    context.startActivity(intent);
                }
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

    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform());
        }
    }
}