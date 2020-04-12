package com.example.hw1;

import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Task4Activity extends AppCompatActivity {

    private int imageIndex = 0;
    private MovieData data = new MovieData();
    private int multiplier = 10;

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

            }
        });
    }
}
