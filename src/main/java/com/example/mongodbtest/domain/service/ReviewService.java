package com.example.mongodbtest.domain.service;

import com.example.mongodbtest.infrastructure.document.Movie;
import com.example.mongodbtest.infrastructure.document.Review;
import com.example.mongodbtest.infrastructure.repository.ReviewRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

  private final ReviewRepository repository;
  private final MongoTemplate mongoTemplate;


  public Review createReview(String reviewBody, String imdbId) {
    var review = repository.insert(new Review(reviewBody));

    mongoTemplate.update(Movie.class)
      .matching(Criteria.where("imdbId").is(imdbId))
      .apply(new Update().push("reviewIds").value(review))
        .first();
    return review;
  }

  public List<Review> getAllReviews() {
    return repository.findAll();
  }
}
