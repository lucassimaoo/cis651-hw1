package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

public class ListActivity extends AppCompatActivity implements OnItemSelectedListener {

    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dualpane);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ListFragment()).commit();
        }
        twoPane = false;
        if (findViewById(R.id.detail_container) != null) {
            twoPane = true;
        }
    }

    @Override
    public void onListItemSelected(View sharedView, int image, String title, String year) {
        Bundle args = new Bundle();
        args.putInt("img_id", image);
        args.putString("mtitle", title);
        args.putString("myear", year);

        Fragment fragment = new DetailFragment();
        fragment.setArguments(args);
        if (twoPane) {
            fragment.setEnterTransition(new Slide(Gravity.TOP));
            fragment.setExitTransition(new Slide(Gravity.BOTTOM));
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).addToBackStack(null).commit();
        } else {
            fragment.setSharedElementEnterTransition(new DetailsTransition());
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            fragment.setSharedElementReturnTransition(new DetailsTransition());
            getSupportFragmentManager().beginTransaction()
                    .addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView))
                    .replace(R.id.main_container, fragment).addToBackStack(null).commit();
        }
    }

}
