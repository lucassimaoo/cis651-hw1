package com.example.lab5;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import androidx.core.app.ActivityOptionsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
    }


    public void moveRotate(View view) {
        imageView.animate().setDuration(1000).x(500).y(800).rotationYBy(720).scaleX(.4F).scaleY(.4F);
    }

    public void moveBack(View view) {
        imageView.animate().setDuration(1000).x(imageView.getLeft()).y(imageView.getTop()).rotationYBy(720).scaleX(1F).scaleY(1F);
    }

    public void fadeOut(View view) {
        imageView.animate().setDuration(1000).alpha(0F);
    }

    public void fadeIn(View view) {
        imageView.animate().setDuration(1000).alpha(1F);
    }

    public void xmlAnimator(View view) {
        Animator a = AnimatorInflater.loadAnimator(this, R.animator.custom_animator);
        a.setTarget(imageView);
        a.start();;
    }

    public void newActivity(View view) {
        Intent i = new Intent(this, ListActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, "newActivity");
        startActivity(i, optionsCompat.toBundle());
    }
}
