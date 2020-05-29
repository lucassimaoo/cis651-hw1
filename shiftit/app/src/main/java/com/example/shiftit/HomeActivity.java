package com.example.shiftit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends BasicShiftActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final HomeActivity thiz = this;
        ShiftsAdapter adapter = new ShiftsAdapter(new OnListItemClickListener() {
            @Override
            public void onItemClick(View v, final Shift shift) {
                AlertDialog.Builder builder = new AlertDialog.Builder(thiz);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        repository.markAsDone(shift);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                builder.setMessage("Mark as done?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }, new AssignedShiftsDataProvider(repository, currentUser.getUid()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_home;
    }
}
