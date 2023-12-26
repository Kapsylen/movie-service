package com.example.mongodbtest.infrastructure.entity;

import com.example.mongodbtest.application.UserView;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDoc implements Serializable{
  @Id
  String id;
  String name;

  public static UserView toUserView(UserDoc userDoc) {
    return new UserView(userDoc.getId(), userDoc.getName());
  }
}
