package com.example.lab3;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentTracker {

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;

    private GestureDetectorCompat mDetector;
    private PersonInfo pi = new PersonInfo();
    private int next = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        loadTheFragment(fragment1);
    }

    @Override
    public void fragmentVisible(String s) {
        TextView tv = findViewById(R.id.title);
        tv.setText(s);
    }

    @Override
    public void goNext() {
        nextFragment(1);
    }

    @Override
    public void goBack() {
        nextFragment(-1);
    }

    @Override
    public void saveNameAndLastName(String name, String last) {
        pi.setName(name);
        pi.setLastname(last);
    }

    @Override
    public void saveCityAndZip(String city, String zip) {
        pi.setCity(city);
        pi.setZip(zip);
    }

    @Override
    public void saveLanguage(String lang) {
        pi.setLanguage(lang);
    }

    @Override
    public void finished() {
        Intent i = new Intent(this, Summary.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("pi", pi);
        startActivity(i);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void nextFragment(int frag) {
        next = limit(next + frag);
        Log.d("nextFragment", String.valueOf(next));
        switch (next) {
            case 1: loadTheFragment(fragment1);
                    break;
            case 2: loadTheFragment(fragment2);
                    break;
            case 3: loadTheFragment(fragment3);
                    break;
        }
    }

    public int limit(int value) {
        return Math.max(1, Math.min(value, 3));
    }

    private void loadTheFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        t.replace(R.id.fragment_container, f);
        t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        t.commit();
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() < e2.getX()) {
                Toast.makeText(MainActivity.this, "Fling right", Toast.LENGTH_SHORT).show();
                nextFragment(1);
            } else {
                Toast.makeText(MainActivity.this, "Fling left", Toast.LENGTH_SHORT).show();
                nextFragment(-1);
            }
            return true;
        }
    }
}
