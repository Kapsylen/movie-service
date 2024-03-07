package com.example.mongodbtest.domain.service;

import static com.example.mongodbtest.application.MovieApi.toMovieApi;
import static com.example.mongodbtest.domain.error.ErrorCode.RESOURCE_NOT_FOUND;
import static com.example.mongodbtest.infrastructure.document.Movie.toDocument;

import com.example.mongodbtest.application.MovieApi;
import com.example.mongodbtest.domain.error.ResourceNotFoundException;
import com.example.mongodbtest.infrastructure.MovieRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

  private MovieRepository repository;

  public List<MovieApi> getAllMovies() {
    var list = repository.findAll();
    return list.stream()
        .map(MovieApi::toMovieApi)
        .toList();
  }

  public MovieApi addMovie(MovieApi movieApi) {
    return toMovieApi(repository.save(
        toDocument(movieApi)));
  }
  public void removeAll() {
    repository.deleteAll();
  }

  public Optional<MovieApi> findMovieByImdbId(String imdbId) {
    return Optional.of(toMovieApi(
        repository.findMovieByImdbId(imdbId)
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND, imdbId))
    ));
  }
}
