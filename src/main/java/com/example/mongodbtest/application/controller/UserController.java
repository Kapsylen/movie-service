package com.example.mongodbtest.application.controller;

import com.example.mongodbtest.domain.service.UserService;
import com.example.mongodbtest.application.UserView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

  private UserService service;

  @Operation(summary = "Create a user by name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserView.class)) }),
      @ApiResponse(responseCode = "400", description = "Name can not be empty",
          content = @Content) })
  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  public UserView createUser(@RequestBody UserView userView) {
    return service.addUser(userView);
  }

  @Operation(summary = "Get all users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List all users, can be empty",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserView.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content) })
  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  public List<UserView> getAllUsers() {
    return service.getAllUsers();
  }

  @Operation(summary = "Delete a user by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Deleted user",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserView.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content) })
  @DeleteMapping("/user/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUser(@PathVariable String userId) {
    service.deleteUser(userId);
  }

  @Operation(summary = "Update an existing user by id and name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserView.class)) }),
      @ApiResponse(responseCode = "400", description = "Id or name can not be empty",
          content = @Content) })
  @PutMapping("/user")
  public UserView updateUser(@RequestBody UserView userView) {
    return service.updateUser(userView);
  }

  @Operation(summary = "Get a user by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get a user by id",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = UserView.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content) })
  @GetMapping("/user/{userId}")
  public UserView getUser(@PathVariable String userId) {
    return service.getUser(userId);
  }
}
