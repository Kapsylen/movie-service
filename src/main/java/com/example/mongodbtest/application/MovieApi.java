package com.example.mongodbtest.application;

import com.example.mongodbtest.infrastructure.document.Movie;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieApi implements Serializable {
  private ObjectId id;
  private String imdbId;
  private String title;
  private String releaseDate;
  private String trailerLink;
  private String poster;
  private List<String> genres;
  private List<String> backdrops;
  private List<ReviewApi> reviewIds;

  public static MovieApi toMovieApi(Movie movie) {
    MovieApi movieApi = new MovieApi();
    movieApi.setId(movie.getId());
    movieApi.setImdbId(movie.getImdbId());
    movieApi.setTitle(movie.getTitle());
    movieApi.setReleaseDate(movie.getReleaseDate());
    movieApi.setTrailerLink(movie.getTrailerLink());
    movieApi.setPoster(movie.getPoster());

    movieApi.setGenres(movie.getGenres().stream()
        .map(String::new)
        .toList());
    movieApi.setBackdrops(movie.getBackdrops().stream()
        .map(String::new)
        .toList());
    if(movie.getReviewIds() == null || movie.getReviewIds().isEmpty()){
      return movieApi;
    } else  {
      movieApi.setReviewIds(movie.getReviewIds().stream().map(ReviewApi::toReviewApi).toList());
      return movieApi;
    }
  }
}
