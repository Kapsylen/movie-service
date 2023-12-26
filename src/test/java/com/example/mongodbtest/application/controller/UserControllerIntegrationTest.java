package com.example.mongodbtest.application.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.mongodbtest.domain.service.UserService;
import com.example.mongodbtest.application.UserView;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
  @WebMvcTest(value = UserController.class)
  public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void givenOneUserExists_whenGetUsers_thenReturnJsonArrayWithOneUser() throws Exception {
      var user = new UserView(null, "T1");

      var allUsers = List.of(user);

      given(service.getAllUsers()).willReturn(allUsers);

      mvc.perform(get("/api/users")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].name", is(user.getName())));
      verify(service, VerificationModeFactory.times(1)).getAllUsers();
    }

    @Test
    public void whenPostUser_thenCreateUser() throws Exception {
      var userId = UUID.randomUUID().toString();
      var user = new UserView(userId, "T1");
      given(service.addUser(Mockito.any())).willReturn(user);

      mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
          .content(JsonUtil.toJson(user)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id", is(userId)))
          .andExpect(jsonPath("$.name", is("T1")));
      verify(service, VerificationModeFactory.times(1)).addUser(Mockito.any());
      reset(service);
    }

    @Test
    public void givenUserWithUserIdExists_whenDeleteUser_thenUserDeleted() throws Exception {
      var userId = UUID.randomUUID().toString();
      mvc.perform(delete("/api/user/{userId}", userId)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
      verify(service, VerificationModeFactory.times(1)).deleteUser(userId);
    }

    @Test
    public void givenUserWithUserIdExists_whenUpdateUser_thenUserIsUpdated() throws Exception {
      var userId = UUID.randomUUID().toString();
      var user = new UserView(userId, "T1");
      given(service.updateUser(Mockito.any())).willReturn(user);

      mvc.perform(put("/api/user").contentType(MediaType.APPLICATION_JSON)
              .content(JsonUtil.toJson(user)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(userId)))
          .andExpect(jsonPath("$.name", is("T1")));
      verify(service, VerificationModeFactory.times(1)).updateUser(Mockito.any());
    }

    @Test
    public void givenUserWithUserIdExists_whenGetUser_thenReturnUser() throws Exception {
      var userId = UUID.randomUUID().toString();
      var user = new UserView(userId, "T1");
      given(service.getUser(Mockito.any())).willReturn(user);
      mvc.perform(get("/api/user/{userId}", userId)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(userId)))
          .andExpect(jsonPath("$.name", is("T1")));
      verify(service, VerificationModeFactory.times(1)).getUser(userId);
    }
  static class JsonUtil {
    static byte[] toJson(Object object) throws IOException {
      ObjectMapper mapper = new ObjectMapper();
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      return mapper.writeValueAsBytes(object);
    }
  }
}
