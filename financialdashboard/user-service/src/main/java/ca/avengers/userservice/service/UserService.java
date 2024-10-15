package ca.avengers.userservice.service;

import ca.avengers.userservice.dto.LoginRequest;
import ca.avengers.userservice.dto.UserRequest;
import ca.avengers.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {
    void createUser(UserRequest userRequest);

    String updateUser(String userId, UserRequest userRequest);

    void deleteUser(String userId);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(String userId);

    UserResponse getUserByUserName(String userName);

    UserResponse login(LoginRequest loginRequest);
}
