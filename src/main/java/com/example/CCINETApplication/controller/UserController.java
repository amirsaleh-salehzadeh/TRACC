package com.example.CCINETApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.CCINETApplication.exception.NotFoundException;
import com.example.CCINETApplication.model.User;
import com.example.CCINETApplication.repository.UserRepository;

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
	public ResponseEntity<List<User>> listUsers() {
		List<User> users = userRepository.findAll();
		return ResponseEntity.ok(users);
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists."); // Username conflict
		}
		User savedUser = userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

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
