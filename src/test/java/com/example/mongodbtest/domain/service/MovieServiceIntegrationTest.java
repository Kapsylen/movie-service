package com.example.mongodbtest.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.mongodbtest.application.MovieApi;
import com.example.mongodbtest.application.ReviewApi;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MovieServiceIntegrationTest {

  @Autowired
  MovieService movieReview;

  @Autowired
  ReviewService reviewService;

  @Container
  private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0")
      .withExposedPorts(27017);

  @DynamicPropertySource
  static void mongoDbProperties(DynamicPropertyRegistry registry) {
    mongoDBContainer.start();
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }


  @BeforeEach
  void setup(){
    movieReview.removeAll();
  }


  @Test
  void givenAdMovie_whenGetMovieById_thenReturnMovie() {
    var movie = new MovieApi();
    movie.setTitle("Movie 1");
    movie.setPoster("Action");
    movie.setTrailerLink("");
    movie.setImdbId(UUID.randomUUID().toString());
    var review1 = new ReviewApi(null,"r1");
    movie.setReviewIds(List.of(review1));
    movie.setGenres(List.of(""));
    movie.setBackdrops(List.of(""));

    var savedMovie = movieReview.addMovie(movie);

    var fetchedMovie = movieReview.findMovieByImdbId(savedMovie.getImdbId());

    assertNotNull(fetchedMovie);
    assertNotNull(fetchedMovie.getId());
    assertEquals(movie.getTitle(), fetchedMovie.getTitle());
  }

  @Test
  void givenTwoMoviesAreStored_whenGetAllMovies_thenReturnTwoMovies(){
    var movie1 = new MovieApi();
    movie1.setTitle("Movie 1");
    movie1.setPoster("Action");
    movie1.setTrailerLink("");
    movie1.setImdbId(UUID.randomUUID().toString());
    var review1 = new ReviewApi(null,"r1");
    movie1.setReviewIds(List.of(review1));
    movie1.setGenres(List.of(""));
    movie1.setBackdrops(List.of(""));

    var movie2 = new MovieApi();
    movie2.setTitle("Movie 2");
    movie2.setPoster("Comedy");
    movie2.setTrailerLink("");
    movie2.setImdbId(UUID.randomUUID().toString());
    var review2 = new ReviewApi(null,"r2");
    movie2.setReviewIds(List.of(review2));
    movie2.setGenres(List.of(""));
    movie2.setBackdrops(List.of(""));

    movieReview.addMovie(movie1);
    movieReview.addMovie(movie2);
    var users = movieReview.getAllMovies();
    assertEquals(2, users.size());
  }

  @Test
  void givenMovieAreStored_whenGetMovieByImdId_thenReturnMovie() {
    var movie = new MovieApi();
    movie.setTitle("Movie 1");
    movie.setPoster("Action");
    movie.setTrailerLink("");
    movie.setImdbId(UUID.randomUUID().toString());
    movie.setGenres(List.of(""));
    movie.setBackdrops(List.of(""));

    var storedMovie = movieReview.addMovie(movie);
    var fetchedMovie = movieReview.findMovieByImdbId(storedMovie.getImdbId());

    assertNotNull(fetchedMovie);
    assertEquals(storedMovie.getTitle(), fetchedMovie.getTitle());
    assertEquals(storedMovie.getPoster(), fetchedMovie.getPoster());
    assertEquals(storedMovie.getTrailerLink(), fetchedMovie.getTrailerLink());
    assertEquals(storedMovie.getImdbId(), fetchedMovie.getImdbId());
    assertEquals(storedMovie.getGenres(), fetchedMovie.getGenres());
    assertEquals(storedMovie.getBackdrops(), fetchedMovie.getBackdrops());
    assertEquals(storedMovie.getId(), fetchedMovie.getId());
    assertNull(fetchedMovie.getReviewIds());

  }

  @Test
  void givenMovieIsStoredAndCreateReviewWithImdbIdAnd_whenGetMovie_MovieShouldIncludeListReview() {
    var movie = new MovieApi();
    movie.setTitle("Movie 1");
    movie.setPoster("Action");
    movie.setTrailerLink("");
    movie.setImdbId(UUID.randomUUID().toString());
    movie.setGenres(List.of(""));
    movie.setBackdrops(List.of(""));
    movie.setReviewIds(List.of());
    String reviewBody = "This movie is great!";

    var storedMovie = movieReview.addMovie(movie);
    var review = reviewService.createReview(reviewBody, storedMovie.getImdbId());
    var updatedMovie = movieReview.findMovieByImdbId(storedMovie.getImdbId());

    assertEquals(reviewBody, review.getBody());
    assertNotNull(updatedMovie.getReviewIds());
    assertEquals(1, updatedMovie.getReviewIds().size());
  }
}
