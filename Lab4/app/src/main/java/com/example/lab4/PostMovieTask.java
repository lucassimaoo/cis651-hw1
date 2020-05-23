package com.example.lab4;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostMovieTask extends AsyncTask<String, Void, Void> {

    private MyRecyclerAdapter adapter;
    private Movie movie;
    private ObjectMapper mapper = new ObjectMapper();

    public PostMovieTask(MyRecyclerAdapter adapter, Movie m) {
        this.movie = new Movie();
        movie.setDescription(m.getDescription());
        movie.setDirector(m.getDirector());
        movie.setId(m.getId() + "_new");
        movie.setLength(m.getLength());
        movie.setRating(m.getRating());
        movie.setStars(m.getStars());
        movie.setTitle(m.getTitle());
        movie.setUrl(m.getUrl());
        movie.setYearMade(m.getYearMade());
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... url) {
        try {
            Utils.sendHttPostRequest(url[0], mapper.writeValueAsString(movie));
        } catch (JsonProcessingException e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        adapter.getMd_filtered().add(movie);
        adapter.notifyItemChanged(adapter.getMd_filtered().size() - 1);
        adapter.getRv().scrollToPosition(adapter.getMd_filtered().size() - 1);
    }
}
