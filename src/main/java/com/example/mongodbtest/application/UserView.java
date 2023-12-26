package com.example.mongodbtest.application;

import com.example.mongodbtest.infrastructure.entity.UserDoc;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserView implements Serializable{
    String id;
    String name;


  public static UserDoc toUserDoc(UserView user) {
    var newUser = new UserDoc();
    newUser.setName(user.name);
    return newUser;
  }
}
