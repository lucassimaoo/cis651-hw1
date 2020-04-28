package com.example.lab4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements Filterable {

    private List<Map<String, ?>> md;
    private List<Map<String, ?>> md_filtered;
    private OnListItemClickListener onListItemClickListener;

    public MyRecyclerAdapter(List<Map<String, ?>> list) {
        md = md_filtered = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onListItemClickListener != null) {
                    onListItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
                }
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onListItemClickListener != null) {
                    onListItemClickListener.onItemLongClick(v, viewHolder.getAdapterPosition());
                }
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(md_filtered.get(position).get("name").toString());
        holder.year.setText(md_filtered.get(position).get("year").toString());
        holder.poster.setImageResource(Integer.parseInt(md_filtered.get(position).get("image").toString()));
    }

    @Override
    public int getItemCount() {
        return md_filtered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase();
                if (charString.trim().isEmpty()) {
                    md_filtered = md;
                } else {
                    List<Map<String, ?>> filteredList = new ArrayList<>();
                    for (Map movie: md) {
                        if (movie.get("name").toString().toLowerCase().contains(charString)) {
                            filteredList.add(movie);
                        }
                    }
                    md_filtered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = md_filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                md_filtered = (List<Map<String, ?>>) results.values;
                notifyDataSetChanged();
            }
        };
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

    public void setOnListItemClickListener(OnListItemClickListener l) {
        this.onListItemClickListener = l;
    }

    public Map getItem(int position) {
        return md_filtered.get(position);
    }
}