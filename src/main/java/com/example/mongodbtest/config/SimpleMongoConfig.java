
package com.example.mongodbtest.config;

import com.example.mongodbtest.infrastructure.UserRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class SimpleMongoConfig {

  @Bean
  public MongoClient mongo()  {
    final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
    final MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString).build();
    return MongoClients.create(settings);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongo(), "test");
  }
}
