package com.example.hw1;

import android.graphics.drawable.Drawable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class Task4Activity extends AppCompatActivity {

    private int imageIndex = 0;
    private MovieData data = new MovieData();
    private int multiplier = 10;
    private GestureDetectorCompat gd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task4);

        final SeekBar sb = findViewById(R.id.seek);

        final ImageView img = findViewById(R.id.img);
        Drawable myIcon = getResources().getDrawable( (int)data.getItem(imageIndex).get("image") );
        img.setBackground(myIcon);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(sb.getProgress() * multiplier, sb.getProgress() * multiplier);
        img.setLayoutParams(layoutParams);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(progress * multiplier, progress * multiplier);
                img.setLayoutParams(layoutParams);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        final Task4Activity thiz = this;

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gd.onTouchEvent(event);
            }
        });

        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sb.setProgress(50);
                return true;
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(thiz, "Toast!" , Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Snack!", Snackbar.LENGTH_SHORT).show();
            }
        });

        gd = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() < e2.getX()) {
                    if (imageIndex > 0) {
                        Drawable myIcon = getResources().getDrawable( (int)data.getItem(--imageIndex).get("image") );
                        img.setBackground(myIcon);
                    }
                } else {
                    if (imageIndex < data.getSize()) {
                        Drawable myIcon = getResources().getDrawable((int) data.getItem(++imageIndex).get("image"));
                        img.setBackground(myIcon);
                    }
                }
                return true;
            }
        });
    }
}
