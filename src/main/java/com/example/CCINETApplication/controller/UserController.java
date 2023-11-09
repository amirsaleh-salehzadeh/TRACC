package com.example.CCINETApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
//import io.swagger.annotations.*;

import com.example.CCINETApplication.exception.NotFoundException;
import com.example.CCINETApplication.model.User;
import com.example.CCINETApplication.repository.UserRepository;

//import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping
//	@ApiOperation("Get a list of all users")
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "List of users retrieved successfully"),
//        @ApiResponse(code = 500, message = "Internal server error")
//    })
	public ResponseEntity<List<User>> listUsers() {
		List<User> users = userRepository.findAll();
		return ResponseEntity.ok(users);
	}

	@PostMapping
//	@ApiOperation("Create a new user")
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "User created successfully"),
//        @ApiResponse(code = 409, message = "Conflict - Username already exists")
//    })
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists."); // Username conflict
		}
		User savedUser = userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

//	@ApiOperation("Get information about a specific user")
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "User information retrieved successfully"),
//        @ApiResponse(code = 404, message = "User not found")
//    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = optionalUser.get();
        return ResponseEntity.ok(user);
    }
    
	@PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        User existingUser = optionalUser.get();

        // Check if the new username already exists (except for the existing user)
        if (!existingUser.getUsername().equals(updatedUser.getUsername())
                && userRepository.existsByUsername(updatedUser.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        // Update user details
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setFirstname(updatedUser.getFirstname());
        existingUser.setSurname(updatedUser.getSurname());

        User savedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }
}
