package com.example.mongodbtest.application;

import com.example.mongodbtest.infrastructure.document.Review;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewApi implements Serializable {
  ObjectId id;
  String name;
  public static ReviewApi toReviewApi(Review review) {
    return new ReviewApi(review.getId(), review.getBody());
  }
}
