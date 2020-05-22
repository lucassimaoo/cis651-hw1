package com.master.mobile.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {

  @Id
  private String id;

  private String title;

  private String yearMade;

  private String director;

  private Double rating;

  private String url;

  private String length;

  private String stars;

  private String description;

  public Movie() {

  }

  public Movie(String title, String yearMade, String director, Double rating, String url, String length,
    String description, String stars) {
    this.title = title;
    this.yearMade = yearMade;
    this.director = director;
    this.rating = rating;
    this.url = url;
    this.length = length;
    this.description = description;
    this.stars = stars;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getYearMade() {
    return yearMade;
  }

  public void setYearMade(String yearMade) {
    this.yearMade = yearMade;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStars() {
    return stars;
  }

  public void setStars(String stars) {
    this.stars = stars;
  }
}
