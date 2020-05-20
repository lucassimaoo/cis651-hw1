package com.example.lab4;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements Filterable {

    private RequestQueue requestQueue;
    private List<Map<String, ?>> md;

    private List<Movie> movies;

    private List<Map<String, ?>> md_filtered;
    private OnListItemClickListener onListItemClickListener;
    private MainActivity mainActivity;
    private ObjectMapper mapper = new ObjectMapper();

    public MyRecyclerAdapter(List<Map<String, ?>> list, final MainActivity mainActivity) {
        this.md = md_filtered = list;
        this.mainActivity = mainActivity;

        requestQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());

        String url ="http://192.168.56.1:8080/movies";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        movies = mapper.readValue(response.toString(),
                                new TypeReference<List<Movie>>() {});

                    } catch (JsonProcessingException e) {
                        Toast.makeText(mainActivity, "Failed to parse movie list", Toast.LENGTH_LONG).show();
                    }

                    response.toString();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mainActivity, "Failed to get movie list", Toast.LENGTH_LONG).show();
                }
            });

        requestQueue.add(jsonObjectRequest);
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

        v.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View view, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mainActivity, "del " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                menu.add("Duplicate").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mainActivity, "dup " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(md_filtered.get(position).get("name").toString());
        holder.desc.setText(md_filtered.get(position).get("description").toString());
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
        public TextView desc;
        public TextView name;
        public ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = (TextView) itemView.findViewById(R.id.movie_desc);
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
