package com.rahbod.visit365;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rahbod.visit365.Font.ButtonFont;
import com.rahbod.visit365.Font.FontTextView;
import com.rahbod.visit365.models.DrList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterDrList extends RecyclerView.Adapter<AdapterDrList.DrListViewHolder> {

    private Context context;
    private List<DrList> drLists = new ArrayList<>();

    public AdapterDrList(Context context, List<DrList> drLists) {
        this.context = context;
        this.drLists = drLists;
    }

    @Override
    public DrListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_dr_list, parent, false);
        return new DrListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DrListViewHolder holder, int position) {
        Picasso.with(context).load(drLists.get(position).getAvatar()).into(holder.imgAvatarDr);
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
                Toast.makeText(context, "reserve", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
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
