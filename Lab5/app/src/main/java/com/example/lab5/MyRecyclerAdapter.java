package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<Map<String, ?>> md;
    private OnItemSelectedListener onListItemClickListener;

    public MyRecyclerAdapter(List<Map<String, ?>> list) {
        md = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(md.get(position).get("name").toString());
        holder.year.setText(md.get(position).get("year").toString());
        holder.poster.setImageResource(Integer.parseInt(md.get(position).get("image").toString()));
        ViewCompat.setTransitionName(holder.poster, md.get(position).get("name").toString());
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onListItemClickListener != null) {
                    onListItemClickListener.onListItemSelected(v, Integer.parseInt(md.get(position).get("image").toString()),
                            md.get(position).get("name").toString(), md.get(position).get("year").toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return md.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView year;
        public TextView name;
        public ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            year = (TextView) itemView.findViewById(R.id.movie_year);
            name = (TextView) itemView.findViewById(R.id.movie_name);
            poster = (ImageView) itemView.findViewById(R.id.poster_photo);
        }
    }

    public void setOnListItemClickListener(OnItemSelectedListener l) {
        this.onListItemClickListener = l;
    }

    public Map getItem(int position) {
        return md.get(position);
    }
}
