package com.master.mobile.backend.repository;

import com.master.mobile.backend.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, String> {

  List<Movie> findByRatingGreaterThanEqual(Double rating);

}
