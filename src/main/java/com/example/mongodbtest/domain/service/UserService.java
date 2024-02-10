package com.example.mongodbtest.domain.service;

import static com.example.mongodbtest.infrastructure.entity.UserDoc.toUserView;

import com.example.mongodbtest.domain.error.ErrorCode;
import com.example.mongodbtest.domain.error.ResourceNotFoundException;
import com.example.mongodbtest.infrastructure.entity.UserDoc;
import com.example.mongodbtest.infrastructure.UserRepository;
import com.example.mongodbtest.application.UserView;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository repository;

  public UserView addUser(UserView user) {
    var userDoc = repository.save(UserView.toUserDoc(user));
    return toUserView(userDoc);
  }

  public void deleteUser(String userId) {
    if (repository.findById(userId).isPresent()) {
      repository.deleteById(userId);
    } else {
      throw new ResourceNotFoundException(
          ErrorCode.RESOURCE_NOT_FOUND, "Unable to find a user with userId: " + userId);
    }
  }

  public List<UserView> getAllUsers() {
    var list = repository.findAll();
    return list.stream()
        .map(UserDoc::toUserView)
        .collect(Collectors.toList());
  }

  public UserView updateUser(UserView user) {
    var oldUser = repository.findById(user.getId());
    if (oldUser.isPresent()) {
      var newUser = oldUser.get();
      newUser.setId((user.getId()));
      newUser.setName(user.getName());
      repository.save(newUser);
      return toUserView(newUser);
    } else {
      throw new ResourceNotFoundException(
          ErrorCode.RESOURCE_NOT_FOUND, "Unable to find a user with userId: " + user.getId());
    }
  }

  public UserView getUser(String userId) {
    return toUserView(repository.findById(
            userId)
        .orElseThrow(() -> new ResourceNotFoundException(
            ErrorCode.RESOURCE_NOT_FOUND, "Unable to find a user with userId: " + userId)));
  }

  public void removeAll() {
    repository.deleteAll();
  }
}
