package com.example.shiftit;

import androidx.appcompat.app.AlertDialog;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class HistoryActivity extends BasicShiftActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final HistoryActivity thiz = this;
        HistoryAdapter adapter = new HistoryAdapter(new OnListItemClickListener<History>() {
            @Override
            public void onItemClick(View v, final History history) {
                AlertDialog.Builder builder = new AlertDialog.Builder(thiz, R.style.MyDialogTheme);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        repository.markAsSettled(history.getShifts());
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

                builder.setMessage("Mark as settled?");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_history;
    }
}
