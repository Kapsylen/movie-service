package com.example.mongodbtest.infrastructure.document;

import com.example.mongodbtest.application.MovieApi;
import com.example.mongodbtest.application.ReviewApi;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
  @Id
  private ObjectId id;
  private String imdbId;
  private String title;
  private String releaseDate;
  private String trailerLink;
  private String poster;
  private List<String> genres;
  private List<String> backdrops;
  @DocumentReference
  private List<Review> reviewIds;

  public static Movie toDocument(MovieApi movieApi) {
    Movie movie = new Movie();
    movie.setImdbId(UUID.randomUUID().toString());
    movie.setTitle(movieApi.getTitle());
    movie.setReleaseDate(movieApi.getReleaseDate());
    movie.setTrailerLink(movieApi.getTrailerLink());
    movie.setPoster(movieApi.getPoster());
    movie.setGenres(movieApi.getGenres());
    movie.setBackdrops(movieApi.getBackdrops());
    movie.setGenres(movieApi.getGenres().stream()
        .map(String::new)
        .toList());
    movie.setBackdrops(movieApi.getBackdrops().stream()
        .map(String::new)
        .toList());
    return movie;
  }
}