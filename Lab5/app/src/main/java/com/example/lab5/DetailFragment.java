package com.example.lab5;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        final View v = inflater.inflate(R.layout.detail_view, container, false);
        ImageView i = v.findViewById(R.id.img_poster);
        i.setImageResource(args.getInt("img_id"));

        TextView t = v.findViewById(R.id.title);
        t.setText(args.getString("mtitle"));

        TextView y = v.findViewById(R.id.year);
        y.setText(args.getString("myear"));

        ViewCompat.setTransitionName(i, args.getString("mtitle"));

        return v;
    }
}
