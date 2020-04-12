package com.example.hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void task1(View view) {
        Intent intent = new Intent(this, Task1Activity.class);
        startActivity(intent);
    }

    public void task2(View view) {
        Intent intent = new Intent(this, Task2Activity.class);
        startActivity(intent);
    }

    public void task3(View view) {
        Intent intent = new Intent(this, Task3Activity.class);
        startActivity(intent);
    }
}
