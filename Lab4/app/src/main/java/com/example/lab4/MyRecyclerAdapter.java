package com.example.lab4;

import android.graphics.Bitmap;
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
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements Filterable {

    public static String url ="http://192.168.56.1:8080/movies";

    private RequestQueue requestQueue;

    private Map<String, Bitmap> imageCache = new HashMap<>();

    private List<Movie> movies = new ArrayList<>();

    private List<Movie> md_filtered = new ArrayList<>();
    private OnListItemClickListener onListItemClickListener;
    private MainActivity mainActivity;
    private ObjectMapper mapper = new ObjectMapper();

    public MyRecyclerAdapter(final MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        requestQueue = Volley.newRequestQueue(mainActivity.getApplicationContext());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        movies = mapper.readValue(response.toString(),
                                new TypeReference<List<Movie>>() {});

                        md_filtered = movies;

                    } catch (JsonProcessingException e) {
                        Toast.makeText(mainActivity, "Failed to parse movie list", Toast.LENGTH_LONG).show();
                    }
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

        final MyRecyclerAdapter thiz = this;

        v.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View view, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        final Movie m = md_filtered.get(viewHolder.getAdapterPosition());

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.DELETE, url + "/id/" + m.getId(), null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        thiz.getMovies().remove(m);
                                        thiz.getMd_filtered().remove(m);
                                        thiz.notifyItemRemoved(viewHolder.getAdapterPosition());
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(thiz.mainActivity, "Failed to delete movie", Toast.LENGTH_LONG).show();
                                    }
                                });

                        getRequestQueue().add(jsonObjectRequest);

                        return true;
                    }
                });
                menu.add("Duplicate").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        new PostMovieTask(thiz, md_filtered.get(viewHolder.getAdapterPosition())).execute(url);
                        return true;
                    }
                });
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(md_filtered.get(position).getTitle());
        holder.desc.setText(md_filtered.get(position).getDescription());

        Bitmap image = imageCache.get(md_filtered.get(position).getUrl());
        if (image != null) {
            holder.poster.setImageBitmap(image);
        } else {
            new ImageDownloadTask(holder.poster, this).execute(md_filtered.get(position).getUrl());
        }
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

                boolean isFloat = false;

                try {
                    Float.valueOf(charString);
                    isFloat = true;
                } catch (Exception e) {
                }

                if (charString.trim().isEmpty() || !isFloat) {
                    md_filtered = movies;
                } else  {
                    String url ="http://192.168.56.1:8080/movies/rating/" + charString;

                    String json = Utils.downloadJSONusingHTTPGetRequest(url);

                    try {
                        md_filtered = mapper.readValue(json,
                                new TypeReference<List<Movie>>() {});

                    } catch (JsonProcessingException e) {
                        Toast.makeText(mainActivity, "Failed to parse movie list", Toast.LENGTH_LONG).show();
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = md_filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                md_filtered = (List<Movie>) results.values;
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

    public Movie getItem(int position) {
        return md_filtered.get(position);
    }

    public Map<String, Bitmap> getImageCache() {
        return imageCache;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public List<Movie> getMd_filtered() {
        return md_filtered;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
