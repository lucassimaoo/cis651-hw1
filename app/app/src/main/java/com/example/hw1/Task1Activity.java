package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Task1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);
    }

    public void showMessage(View view) {
        Button btn = (Button) view;
        Toast toast = Toast.makeText(this, btn.getId() + " " + btn.getText(), Toast.LENGTH_SHORT);
        toast.show();
    }
}
