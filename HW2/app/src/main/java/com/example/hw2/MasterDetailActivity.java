package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MasterDetailActivity extends AppCompatActivity {

    private boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dualpane);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ListFragment()).commit();
        }

        if (findViewById(R.id.detail_container) != null) {
            twoPane = true;
        }

    }
}
