package com.master.mobile.backend.rest;

import com.master.mobile.backend.MovieData;
import com.master.mobile.backend.model.Movie;
import com.master.mobile.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/movies")
public class MovieController {

  @Autowired
  private MovieRepository repository;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Movie> getMovies() {

    //hydrate all movies in database
    //repository.saveAll(new MovieData().getMoviesList());

    return  StreamSupport.stream(repository.findAll().spliterator(), false)
      .collect(Collectors.toList());
  }

  @GetMapping(value="/rating/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Movie> getMoviesByRating(@PathVariable("rating") Double rating) {
    return repository.findByRatingGreaterThanEqual(rating);
  }

  @GetMapping(value="/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Movie getMovieById(@PathVariable("id") String id) {
    return repository.findById(id).orElse(null);
  }

  @DeleteMapping(value="/id/{id}")
  public Movie deleteMovieById(@PathVariable("id") String id) {
    Movie m = repository.findById(id).orElse(null);
    repository.deleteById(id);
    return m;
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Movie createMovie(@RequestBody Movie movie) {
    return repository.save(movie);
  }

}
