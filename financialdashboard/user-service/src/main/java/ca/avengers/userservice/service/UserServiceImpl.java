package ca.avengers.userservice.service;

import ca.avengers.userservice.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ObjectMapper objectMapper;

    public UserServiceImpl() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void createUser(UserRequest userRequest) {
        try {
            log.info("Creating a new user: {}", userRequest.getFirstName());

//            File userDataFile = new File("./data/user_data.json");
//
//            if (!userDataFile.exists()) {
//                throw new FileNotFoundException("File not found: " + userDataFile.getAbsolutePath());
//            }
//
//            InputStream inputStream = new FileInputStream(userDataFile);
//            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {
//            });
//            inputStream.close();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/user_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/user_data.json");
            }

            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {});

            boolean userNameExists = users.stream()
                    .anyMatch(user -> user.getUserName().equalsIgnoreCase(userRequest.getUserName()));
            if (userNameExists) {
                throw new IllegalArgumentException("Username already exists: " + userRequest.getUserName());
            }

            Long lastUserId = users.get(users.size() - 1).getId();

            AddressResponse residentialAddress = mapToAddressResponse(userRequest.getResidentialAddress(), 1);
            AddressResponse mailingAddress = mapToAddressResponse(userRequest.getMailingAddress(), 1);

            users.add(UserResponse.builder()
                    .id(lastUserId + 1)
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .userName(userRequest.getUserName())
                    .email(userRequest.getEmail())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .password(userRequest.getPassword())
                    .residentialAddress(residentialAddress)
                    .mailingAddress(mailingAddress)
                    .createdAt(ZonedDateTime.now(ZoneOffset.UTC))
                    .build());

            objectMapper.writeValue(new File("data/user_data.json"), users);

            log.info("User {} is saved.", lastUserId + 1);
        } catch (IOException e) {
            log.error("Error reading user data from JSON file", e);
        } catch (IllegalArgumentException e) {
            log.error("Username already exists", e);
        }
    }

    @Override
    public String updateUser(String userId, UserRequest userRequest) {

        try {
            log.info("Updating a user with id: {}", userId);

//            File userDataFile = new File("./data/user_data.json");
//            if (!userDataFile.exists()) {
//                throw new FileNotFoundException("File not found: " + userDataFile.getAbsolutePath());
//            }
//
//            InputStream inputStream = new FileInputStream(userDataFile);
//            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {
//            });
//            inputStream.close();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/user_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/user_data.json");
            }
            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {});

            UserResponse user = users.stream()
                    .filter(u -> u.getId().equals(Long.parseLong(userId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));

            AddressResponse residentialAddress = mapToAddressResponse(userRequest.getResidentialAddress(), user.getResidentialAddress().getId());
            AddressResponse mailingAddress = mapToAddressResponse(userRequest.getMailingAddress(), user.getMailingAddress().getId());

            users.forEach(u -> {
                if (u.getId().equals(Long.parseLong(userId))) {
                    u.setFirstName(userRequest.getFirstName());
                    u.setLastName(userRequest.getLastName());
                    u.setUserName(userRequest.getUserName());
                    u.setEmail(userRequest.getEmail());
                    u.setPhoneNumber(userRequest.getPhoneNumber());
                    u.setPassword(userRequest.getPassword());
                    u.setResidentialAddress(residentialAddress);
                    u.setMailingAddress(mailingAddress);
                    u.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
                }
            });

            objectMapper.writeValue(new File("data/user_data.json"), users);

            log.info("User {} is updated", userId);
            return userId;
        } catch (IOException e) {
            log.error("Error reading user data from JSON file", e);
            return userId;
        }
    }

    @Override
    public void deleteUser(String userId) {
        try {
            log.info("Deleting a user with id: {}", userId);

//            File userDataFile = new File("./data/user_data.json");
//            if (!userDataFile.exists()) {
//                throw new FileNotFoundException("File not found: " + userDataFile.getAbsolutePath());
//            }
//            InputStream inputStream = new FileInputStream(userDataFile);
//            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {
//            });
//            inputStream.close();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/user_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/user_data.json");
            }
            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {});

//            users.removeIf(user -> user.getId().equals(Long.parseLong(userId)));
            users.forEach(user -> {
                if (user.getId().equals(Long.parseLong(userId))) {
                    user.setDeletedAt(ZonedDateTime.now(ZoneOffset.UTC));
                }
            });

            objectMapper.writeValue(new File("data/user_data.json"), users);


            log.info("User {} is deleted", userId);
        } catch (IOException e) {
            log.error("Error reading user data from JSON file", e);
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Retrieving a list of all users");
        try {
//            List<UserResponse> users = getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/user_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/user_data.json");
            }
            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {});

            log.info("Returning all users");
            return users;
        } catch (IOException e) {
            log.error("Error reading user data from JSON file", e);
            return Collections.emptyList();
        }
    }

    @Override
    public UserResponse getUserById(String userId) {
        log.info("Retrieving a user with id: {}", userId);
        try {
//            List<UserResponse> users = getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/user_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/user_data.json");
            }
            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {});

            UserResponse user = users.stream()
                    .filter(u -> u.getId().equals(Long.parseLong(userId)))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));

            log.info("Returning user with id: {}", userId);
            return user;
        } catch (IOException e) {
            log.error("Error reading user data from JSON file", e);
            return null;
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving user by id", e);
            return null;
        }
    }

    @Override
    public UserResponse getUserByUserName(String username) {
        log.info("Retrieving a user with username: {}", username);
        try {
//            List<UserResponse> users = getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/user_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/user_data.json");
            }
            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {});

            UserResponse user = users.stream()
                    .filter(u -> u.getUserName().equalsIgnoreCase(username))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));

            log.info("Returning user with username: {}", username);
            return user;
        } catch (IOException e) {
            log.error("Error reading user data from JSON file", e);
            return null;
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving user by username", e);
            return null;
        }
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {

        log.info("Logging in user with username: {}", loginRequest.getUserName());

        if (loginRequest.getUserName() == null || loginRequest.getUserName().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {
//            List<UserResponse> users = getUserResponseList();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/user_data.json");
            if (inputStream == null) {
                throw new FileNotFoundException("File not found: data/user_data.json");
            }
            List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {});

            UserResponse user = users.stream()
                    .filter(u -> u.getUserName().equalsIgnoreCase(loginRequest.getUserName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!user.getPassword().equals(loginRequest.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            log.info("Login successful for user: {}", loginRequest.getUserName());
            return user;
        } catch (IOException e) {
            log.error("Error reading user data from JSON file", e);
            return null;
        } catch (IllegalArgumentException e) {
            log.error("Error logging in user", e);
            return null;
        }
    }


    // Helper methods:
    private List<UserResponse> getUserResponseList() throws IOException {
        File userDataFile = new File("./data/user_data.json");
        if (!userDataFile.exists()) {
            throw new FileNotFoundException("File not found: " + userDataFile.getAbsolutePath());
        }

        InputStream inputStream = new FileInputStream(userDataFile);
        List<UserResponse> users = objectMapper.readValue(inputStream, new TypeReference<List<UserResponse>>() {
        });
        inputStream.close();

        return users;
    }

    private AddressResponse mapToAddressResponse(AddressRequest addressRequest, long addressId) {
        return AddressResponse.builder()
                .id(addressId)
                .street(addressRequest.getStreet())
                .street2(addressRequest.getStreet2())
                .city(addressRequest.getCity())
                .province(addressRequest.getProvince())
                .postalCode(addressRequest.getPostalCode())
                .country(addressRequest.getCountry())
                .build();
    }

}
