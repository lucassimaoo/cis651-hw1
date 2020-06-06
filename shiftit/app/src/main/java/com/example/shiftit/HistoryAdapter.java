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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements Adapter {

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private List<History> list;
    private Repository repository;
    private OnListItemClickListener<History> clickListener;

    public HistoryAdapter(OnListItemClickListener<History> clickListener) {
        this.clickListener = clickListener;
        repository = Repository.getInstance();
        repository.addAdapter(this);
        updateList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_history, parent,false);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(v, list.get(vh.getAdapterPosition()));
            }
        });
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = list.get(position);
        holder.name_view.setText("Taker: " + repository.getUser(history.getUid()).getName());
        holder.hours_view.setText("Hours: " + history.getHours());

        User requester = repository.getUser(history.getUid());
        if (requester.getPicture() != null) {
            Picasso.get().load(requester.getPicture()).transform(new CircleTransform()).into(holder.image_view);
        } else {
            holder.image_view.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name_view;
        public TextView hours_view;
        public ImageView image_view;
        public ViewHolder(View v){
            super(v);
            name_view = v.findViewById(R.id.name_view);
            hours_view = v.findViewById(R.id.hours_view);
            image_view = v.findViewById(R.id.image_view);
        }
    }

    public void updateList() {
        list = repository.getHistory();
        notifyDataSetChanged();
    }

}
