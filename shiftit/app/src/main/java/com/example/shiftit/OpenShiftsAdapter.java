package com.example.shiftit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.List;

public class OpenShiftsAdapter extends RecyclerView.Adapter<OpenShiftsAdapter.ViewHolder> {

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private List<Shift> shiftList;
    private Repository repository;
    private FirebaseUser currentUser;
    private OnListItemClickListener clickListener;

    public OpenShiftsAdapter(FirebaseUser currentUser, OnListItemClickListener clickListener) {
        this.currentUser = currentUser;
        this.clickListener = clickListener;
        repository = Repository.getInstance();
        repository.setOpenShiftAdapter(this);
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
        holder.hours_view.setText("Hours: "+ shift.getHours());

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
        public ViewHolder(View v){
            super(v);
            name_view = v.findViewById(R.id.name_view);
            date_view = v.findViewById(R.id.date_view);
            hospital_view =  v.findViewById(R.id.hospital_view);
            hours_view = v.findViewById(R.id.hours_view);
        }
    }

    public void updateShiftList() {
        Log.d("updateShiftList", "updateShiftList");
        shiftList = repository.getOpenShiftsByProfession(repository.getUser(currentUser.getUid()).getProfession());
        notifyDataSetChanged();
    }

}
