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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment {

    private MovieData md = new MovieData();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.master, container, false);

        ListView listView = v.findViewById(R.id.list_view);

        List<String> names = new ArrayList<>();
        for (Map movie : md.getMoviesList()) {
            names.add(movie.get("name").toString());
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.listview_item, names.toArray());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map map = md.getItem(position);
                FragmentManager sfm = getFragmentManager();
                FragmentTransaction t = sfm.beginTransaction();
                MovieDetailFragment frag = MovieDetailFragment.newInstance((int) map.get("image"), map.get("name").toString(), map.get("year").toString(),
                        Float.parseFloat(map.get("rating").toString()), map.get("description").toString());

                if (container.findViewById(R.id.detail_container) != null) {
                    t.replace(R.id.detail_container, frag);
                } else {
                    t.replace(R.id.main_container, frag);
                }

                t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                t.commit();
            }
        });

        return v;
    }
}
