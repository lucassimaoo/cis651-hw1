package com.example.shiftit;


import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class OpenShiftsActivity extends BasicShiftActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final OpenShiftsActivity thiz = this;
        ShiftsAdapter adapter = new ShiftsAdapter(new OnListItemClickListener() {
            @Override
            public void onItemClick(View v, final Shift shift) {
                AlertDialog.Builder builder = new AlertDialog.Builder(thiz, R.style.MyDialogTheme);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        shift.setTakerUid(currentUser.getUid());
                        repository.save(shift);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                builder.setMessage("Do you want to take this shift for yourself?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }, new OpenShiftsDataProvider(repository, currentUser.getUid()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_open_shifts;
    }
}
