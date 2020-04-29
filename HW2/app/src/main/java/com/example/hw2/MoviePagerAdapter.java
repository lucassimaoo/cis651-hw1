package com.example.hw2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Map;

public class MoviePagerAdapter extends FragmentStatePagerAdapter {

    private MovieData md = new MovieData();

    public MoviePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Map map = md.getItem(position);
        MovieDetailFragment frag = MovieDetailFragment.newInstance((int) map.get("image"), map.get("name").toString(), map.get("year").toString(),
                Float.parseFloat(map.get("rating").toString()), map.get("description").toString());
        return frag;
    }

    @Override
    public int getCount() {
        return md.getSize();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return md.getItem(position).get("name").toString();
    }
}
