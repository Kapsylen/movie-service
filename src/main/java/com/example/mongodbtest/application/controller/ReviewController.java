package com.example.mongodbtest.application.controller;

import com.example.mongodbtest.application.MovieApi;
import com.example.mongodbtest.domain.service.ReviewService;
import com.example.mongodbtest.infrastructure.document.Review;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;


  @Operation(summary = "Add Review")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Review a movie",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = MovieApi.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid input",
          content = @Content)})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Review createReview(@RequestBody Map<String, String> payload) {
    // TODO - Add proper swagger documentation for Map input values
    return reviewService.createReview(payload.get("reviewBody"), payload.get("imdbId"));
  }

  @GetMapping("/all")
  public List<Review> getAllReviews() {
    return reviewService.getAllReviews();
  }
}
