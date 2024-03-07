package com.example.mongodbtest.infrastructure.repository;

import com.example.mongodbtest.infrastructure.document.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

}
