package com.example.shiftit;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class MyShiftsActivity extends BasicShiftActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MyShiftsActivity thiz = this;
        ShiftsAdapter adapter = new ShiftsAdapter(currentUser, new OnListItemClickListener() {
            @Override
            public void onItemClick(View v, final Shift shift) {

                boolean isShiftTaken = shift.getTakerUid() != null;

                AlertDialog.Builder builder = new AlertDialog.Builder(thiz);

                if (isShiftTaken) {
                    builder.setMessage("This shift is taken by another person, you can't delete it");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                } else {
                    builder.setMessage("Delete this shift?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            repository.remove(shift);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                }

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }, new MyShiftsDataProvider(repository, currentUser.getUid()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_shifts;
    }
}
