package com.example.mongodbtest.application.controller;

import com.example.mongodbtest.application.MovieApi;
import com.example.mongodbtest.domain.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MovieController {

  private MovieService service;

  @Operation(summary = "Get all users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List all movies, can be empty",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = MovieApi.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content)})
  @GetMapping("/movies")
  @ResponseStatus(HttpStatus.OK)
  public List<MovieApi> getAllUsers() {
    return service.getAllMovies();
  }

  @Operation(summary = "Add a movie")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Add a new movie",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = MovieApi.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid input",
          content = @Content)})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public MovieApi addMovie(@RequestBody MovieApi movie ) {
    return service.addMovie(movie);
  }

  @Operation(summary = "Get movie by imdbId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get a movie by imdbId",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = MovieApi.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Movie not found",
          content = @Content)})
  @ResponseStatus(HttpStatus.CREATED)
  @GetMapping("/movie/{imdbId}")
  public Optional<MovieApi> movieByImdbId(@PathVariable String imdbId) {
    return service.findMovieByImdbId(imdbId);
  }

  @Operation(summary = "Remove all movies")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Remove all movies")})
  @DeleteMapping("/movies")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeAllMovies() {
    service.removeAll();
  }

}
