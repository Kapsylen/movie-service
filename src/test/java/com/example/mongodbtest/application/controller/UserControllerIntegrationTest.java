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

import com.example.mongodbtest.application.MovieApi;
import com.example.mongodbtest.domain.service.MovieService;
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
  @WebMvcTest(value = MovieController.class)
  public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService service;

    @Test
    public void givenOneUserExists_whenGetUsers_thenReturnJsonArrayWithOneUser() throws Exception {
      var user = new MovieApi();

      var allUsers = List.of(user);

      given(service.getAllMovies()).willReturn(allUsers);

      mvc.perform(get("/api/v1/movies")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].title", is(user.getTitle())));
      verify(service, VerificationModeFactory.times(1)).getAllMovies();
    }
}
