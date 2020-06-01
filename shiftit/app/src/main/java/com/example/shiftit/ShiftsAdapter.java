package com.example.shiftit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class ShiftsAdapter extends RecyclerView.Adapter<ShiftsAdapter.ViewHolder> {

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private List<Shift> shiftList;
    private Repository repository;
    private OnListItemClickListener clickListener;
    private ShiftDataProvider provider;

    public ShiftsAdapter(OnListItemClickListener clickListener, ShiftDataProvider provider) {
        this.clickListener = clickListener;
        this.provider = provider;
        repository = Repository.getInstance();
        repository.addAdapter(this);
        updateShiftList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent,false);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, shiftList.get(vh.getAdapterPosition()));
            }
        });
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shift shift = shiftList.get(position);
        holder.name_view.setText("Requester: " + repository.getUser(shift.getUid()).getName());
        holder.date_view.setText("Date: " + format.format(shift.getDate()));
        holder.hospital_view.setText("Hospital: " + shift.getHospital());
        holder.hours_view.setText("Hours: " + shift.getHours());
        if (shift.getTakerUid() != null) {
            holder.taker_view.setText("Assigned to: " + repository.getUser(shift.getTakerUid()).getName());
        } else {
            holder.taker_view.setVisibility(View.GONE);
        }

        if (provider.showRequesterPicture()) {
            User requester = repository.getUser(shift.getUid());
            if (requester.getPicture() != null) {
                Picasso.get().load(requester.getPicture()).transform(new CircleTransform()).into(holder.image_view);
            } else {
                holder.image_view.setVisibility(View.GONE);
            }
        } else if (shift.getTakerUid() != null) {
            User taker = repository.getUser(shift.getTakerUid());
            if (taker.getPicture() != null) {
                Picasso.get().load(taker.getPicture()).transform(new CircleTransform()).into(holder.image_view);
            } else {
                holder.image_view.setVisibility(View.GONE);
            }
        }


    }
    @Override
    public int getItemCount() {
        return shiftList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name_view;
        public TextView date_view;
        public TextView hospital_view;
        public TextView hours_view;
        public TextView taker_view;
        public ImageView image_view;
        public ViewHolder(View v){
            super(v);
            name_view = v.findViewById(R.id.name_view);
            date_view = v.findViewById(R.id.date_view);
            hospital_view =  v.findViewById(R.id.hospital_view);
            hours_view = v.findViewById(R.id.hours_view);
            taker_view = v.findViewById(R.id.taker_view);
            image_view = v.findViewById(R.id.image_view);
        }
    }

    public void updateShiftList() {
        shiftList = provider.getShifts();
        notifyDataSetChanged();
    }

}
