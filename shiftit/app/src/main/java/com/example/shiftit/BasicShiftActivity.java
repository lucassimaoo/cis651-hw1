package com.example.shiftit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class BasicShiftActivity extends AppCompatActivity {

    protected FirebaseAuth auth;
    protected FirebaseUser currentUser;
    protected Repository repository;
    protected RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        repository = Repository.getInstance();

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.signout:
                auth.signOut();
                finish();
                return true;
            case R.id.newshift:
                startActivity(new Intent(this, NewShiftActivity.class));
                return true;
            case R.id.myshifts:
                startActivity(new Intent(this, MyShiftsActivity.class));
                return true;
            case R.id.openshifts:
                startActivity(new Intent(this, OpenShiftsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    protected abstract int getLayoutResourceId();

}
