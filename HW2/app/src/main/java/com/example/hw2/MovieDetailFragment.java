package com.example.hw2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MovieDetailFragment extends Fragment {

    public static MovieDetailFragment newInstance(int i, String t, String y, float r, String d) {
        MovieDetailFragment f = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id", i);
        args.putString("title", t);
        args.putString("year", y);
        args.putFloat("rating", r);
        args.putString("description", d);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        final View v = inflater.inflate(R.layout.movie_fragment_layout, container, false);
        ImageView i = v.findViewById(R.id.large_image);
        i.setImageResource(args.getInt("id"));

        EditText t = v.findViewById(R.id.title_text);
        t.setText(args.getString("title"));

        EditText y = v.findViewById(R.id.year_text);
        y.setText(args.getString("year"));

        TextView d = v.findViewById(R.id.description);
        d.setText(args.getString("description"));

        RatingBar r = v.findViewById(R.id.movie_rating);
        r.setRating(args.getFloat("rating"));

        return v;
    }
}
