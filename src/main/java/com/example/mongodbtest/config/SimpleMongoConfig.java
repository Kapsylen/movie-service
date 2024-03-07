
package com.example.mongodbtest.config;

import com.example.mongodbtest.infrastructure.MovieRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = MovieRepository.class)
public class SimpleMongoConfig {

  @Value( "${mongodb.path}" )
  private String mdPath;
  @Bean
  public MongoClient mongo()  {
    final ConnectionString connectionString = new ConnectionString(mdPath);
    final MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(connectionString).build();
    return MongoClients.create(settings);
  }
}
