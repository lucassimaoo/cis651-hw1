package com.example.lab4;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

public class MovieListFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;
    private MainActivity mainActivity;

    public MovieListFragment(MyRecyclerAdapter adapter, MainActivity mainActivity) {
        this.adapter = adapter;
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.movie_list_layout, container, false);

        recyclerView = v.findViewById(R.id.mainRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Movie movie = adapter.getItem(position);
                FragmentManager sfm = getFragmentManager();
                FragmentTransaction t = sfm.beginTransaction();
                MovieDetailFragment frag = MovieDetailFragment.newInstance(movie, adapter);

                t.replace(R.id.detailFragment, frag);
                t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                t.addToBackStack(null);
                t.commit();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }
}
