package com.example.hw2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.master, container, false);

        ListView listView = v.findViewById(R.id.list_view);

        MovieData md = new MovieData();
        List<String> names = new ArrayList<>();
        for (Map movie : md.getMoviesList()) {
            names.add(movie.get("name").toString());
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.listview_item, names.toArray());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return v;
    }
}
