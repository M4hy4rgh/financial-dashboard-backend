package ca.avengers.userservice.controller;

import ca.avengers.userservice.dto.LoginRequest;
import ca.avengers.userservice.dto.UserRequest;
import ca.avengers.userservice.dto.UserResponse;
import ca.avengers.userservice.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/userName")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserByUserName(@RequestParam("userName") String userName) {
        return userService.getUserByUserName(userName);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") String userId, @RequestBody UserRequest userRequest) {
        String updatedUserId = userService.updateUser(userId, userRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/user/" + updatedUserId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }


}
