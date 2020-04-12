package com.example.hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.d("MainActivity", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivity", "onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d("MainActivity", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d("MainActivity", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("MainActivity", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "onDestroy");
        super.onDestroy();
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
