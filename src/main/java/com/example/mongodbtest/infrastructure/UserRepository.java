package com.example.mongodbtest.infrastructure;

import com.example.mongodbtest.infrastructure.entity.UserDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDoc, String>{

}
