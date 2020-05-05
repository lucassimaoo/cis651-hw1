package com.example.lab5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ListFragment extends Fragment {
    private OnItemSelectedListener onItemSelectedListener;
    private MovieData md = new MovieData();

    private final MyRecyclerAdapter adapter = new MyRecyclerAdapter(md.getMoviesList());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.mainRecyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onItemSelectedListener = (OnItemSelectedListener) context;
        adapter.setOnListItemClickListener(onItemSelectedListener);
    }
}
