package com.example.mongodbtest.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.example.mongodbtest.domain.error.ResourceNotFoundException;
import com.example.mongodbtest.application.UserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceIntegrationTest {

  @Autowired
  UserService service;

  @Container
  private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0")
      .withExposedPorts(27017);

  @DynamicPropertySource
  static void mongoDbProperties(DynamicPropertyRegistry registry) {
    mongoDBContainer.start();
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @BeforeEach
  void setup(){
    service.removeAll();
  }
  @Test
  void givenAddUser_whenGetUserById_thenReturnAddedUser() {
    var user = new UserView(null, "T1");
    var userReceipt = service.addUser(user);
    var savedUser = service.getUser(userReceipt.getId());

    assertNotNull(savedUser);
    assertNotNull(savedUser.getId());
    assertEquals(user.getName(), savedUser.getName());
  }

  @Test
  void givenUserIsFound_whenDeleteUserById_thenUserWillBeRemoved(){
    var user = new UserView(null, "T1");
    var userReceipt = service.addUser(user);
    service.deleteUser(userReceipt.getId());

    var thrown = assertThrows(ResourceNotFoundException.class, () -> {
      service.getUser(userReceipt.getId());
    });
    assertEquals("We were unable to find a user with userId: "+userReceipt.getId(), thrown.getMessage());
  }

  @Test
  void givenTwoUsersAreStored_whenGetAllUsers_thenReturnTwoUsers(){
    var user1 = new UserView(null, "T1");
    var user2 = new UserView(null, "T2");
    service.addUser(user1);
    service.addUser(user2);
    var users = service.getAllUsers();

    assertEquals(2, users.size());
  }
  @Test
  void givenUserIsSaved_whenGetUserById_thenReturnUser(){
    var user = new UserView(null, "T1");
    var savedUser = service.addUser(user);
    var retrievedUser = service.getUser(savedUser.getId());

    assertNotNull(retrievedUser);
    assertEquals(savedUser.getId(), retrievedUser.getId());
    assertEquals(savedUser.getName(), retrievedUser.getName());
  }
  @Test
  void givenUserIsSaved_whenUpdateUser_userWillBeUpdated() {
    var user = new UserView(null, "T1");
    var savedUser = service.addUser(user);
    var newUser = new UserView(savedUser.getId(), "T2");
    var updatedUser = service.updateUser(newUser);
    var retrievedUser = service.getUser(updatedUser.getId());

    assertNotNull(retrievedUser);
    assertEquals(updatedUser.getId(), retrievedUser.getId());
    assertEquals(updatedUser.getName(), retrievedUser.getName());
  }



}
