package com.example.hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:
                break;
            case R.id.item2:
                break;
            case R.id.item3:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
