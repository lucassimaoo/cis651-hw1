package com.example.lab4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieDetailFragment extends Fragment {

    private MyRecyclerAdapter adapter;
    private ObjectMapper mapper = new ObjectMapper();

    public MovieDetailFragment(MyRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    public static MovieDetailFragment newInstance(Movie movie, MyRecyclerAdapter adapter) {
        MovieDetailFragment f = new MovieDetailFragment(adapter);
        Bundle args = new Bundle();
        args.putString("id", movie.getId());
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_layout, container, false);
        String url ="http://192.168.56.1:8080/movies/id/" + getArguments().getString("id");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Movie m = mapper.readValue(response.toString(), Movie.class);

                            ImageView i = v.findViewById(R.id.large_image);

                            Bitmap image = adapter.getImageCache().get(m.getUrl());
                            i.setImageBitmap(image);

                            TextView t = v.findViewById(R.id.title_text);
                            t.setText(m.getTitle());

                            TextView y = v.findViewById(R.id.year_text);
                            y.setText(m.getYearMade());

                            TextView d = v.findViewById(R.id.description);
                            d.setText(m.getDescription());

                            RatingBar r = v.findViewById(R.id.movie_rating);
                            r.setRating(m.getRating().floatValue());

                            TextView director = v.findViewById(R.id.director);
                            director.setText(m.getDirector());

                            TextView stars = v.findViewById(R.id.stars);
                            stars.setText(m.getStars());

                            TextView length = v.findViewById(R.id.length);
                            length.setText(m.getLength());

                        } catch (JsonProcessingException e) {
                            Toast.makeText(getActivity(), "Failed to movie", Toast.LENGTH_LONG).show();
                        }

                        response.toString();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Failed to get movie", Toast.LENGTH_LONG).show();
                    }
                });

        adapter.getRequestQueue().add(jsonObjectRequest);

        return v;
    }
}
