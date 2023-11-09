package com.example.CCINETApplication;

import com.example.CCINETApplication.controller.UserController;
import com.example.CCINETApplication.exception.NotFoundException;
import com.example.CCINETApplication.model.User;
import com.example.CCINETApplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    void testListUsers() {
        // Mock data
        User user1 = new User(1L, "user1", "John", "Doe", null);
        User user2 = new User(2L, "user2", "Jane", "Doe", null);
        List<User> userList = Arrays.asList(user1, user2);

        // Mock repository behavior
        when(userRepository.findAll()).thenReturn(userList);

        // Test the controller method
        ResponseEntity<List<User>> result = userController.listUsers();

        // Verify the result
        assertEquals(ResponseEntity.ok(userList), result);
    }

    @Test
    void testCreateUser() {
        // Mock data
        User newUser = new User("newUser", "New", "User");

        // Mock repository behavior
        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Test the controller method
        ResponseEntity<?> result = userController.createUser(newUser);

        // Verify the result
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(newUser), result);
    }

    @Test
    void testCreateUserUsernameExists() {
        // Mock data
        User existingUser = new User(1L, "existingUser", "John", "Doe", null);

        // Mock repository behavior
        when(userRepository.existsByUsername(existingUser.getUsername())).thenReturn(true);

        // Test the controller method and expect conflict
        ResponseEntity<?> result = userController.createUser(existingUser);

        // Verify the result
        assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists."), result);
    }

    @Test
    void testGetUserInfo() {
        // Mock data
        Long userId = 1L;
        User user = new User(userId, "user1", "John", "Doe", null);

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Test the controller method
        ResponseEntity<?> result = userController.getUserInfo(userId);

        // Verify the result
        assertEquals(ResponseEntity.ok(user), result);
    }

    @Test
    void testGetUserInfoNotFound() {
        // Mock data
        Long userId = 1L;

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Test the controller method and expect not found
        ResponseEntity<?> result = userController.getUserInfo(userId);

        // Verify the result
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."), result);
    }

    @Test
    void testUpdateUser() throws NotFoundException {
        // Mock data
        Long userId = 1L;
        User existingUser = new User(userId, "existingUser", "John", "Doe", null);
        User updatedUser = new User(userId, "updatedUser", "John", "Doe", null);

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername(updatedUser.getUsername())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Test the controller method
        ResponseEntity<?> result = userController.updateUser(userId, updatedUser);

        // Verify the result
        assertEquals(ResponseEntity.ok(updatedUser), result);
    }

    @Test
    void testUpdateUserNotFound() {
        // Mock data
        Long userId = 1L;
        User updatedUser = new User(userId, "updatedUser", "John", "Doe", null);

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Test the controller method and expect not found
        ResponseEntity<?> result = userController.updateUser(userId, updatedUser);

        // Verify the result
        assertEquals(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."), result);
    }

    @Test
    void testUpdateUserUsernameExists() {
        // Mock data
        Long userId = 1L;
        User existingUser = new User(userId, "existingUser", "John", "Doe", null);
        User updatedUser = new User(userId, "usernameExists", "John", "Doe", null);

        // Mock repository behavior
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername(updatedUser.getUsername())).thenReturn(true);

        // Test the controller method and expect conflict
        ResponseEntity<?> result = userController.updateUser(userId, updatedUser);

        // Verify the result
        assertEquals(ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists."), result);
    }
}
