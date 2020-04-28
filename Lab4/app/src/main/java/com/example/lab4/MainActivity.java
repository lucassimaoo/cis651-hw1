package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieData md = new MovieData();
    private MyRecyclerAdapter adapter = new MyRecyclerAdapter(md.getMoviesList());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Query text=" + query, Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView = findViewById(R.id.mainRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnListItemClickListener(new OnListItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Map map = adapter.getItem(position);
                FragmentManager sfm = getSupportFragmentManager();
                FragmentTransaction t = sfm.beginTransaction();
                MovieDetailFragment frag = MovieDetailFragment.newInstance((int) map.get("image"), map.get("name").toString(), map.get("year").toString(),
                        Float.parseFloat(map.get("rating").toString()), map.get("description").toString());

                t.replace(R.id.detailFragment, frag);
                t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                t.commit();

            }

            @Override
            public void onItemLongClick(View v, int position) {
                Map map = adapter.getItem(position);
                FragmentManager sfm = getSupportFragmentManager();
                FragmentTransaction t = sfm.beginTransaction();
                MovieDetailFragment frag = MovieDetailFragment.newInstance((int) map.get("image"), map.get("name").toString(), map.get("year").toString(),
                        Float.parseFloat(map.get("rating").toString()), map.get("description").toString());

                t.replace(R.id.detailFragment, frag);
                t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                t.commit();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
