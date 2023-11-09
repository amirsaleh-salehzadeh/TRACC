package com.example.CCINETApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.CCINETApplication.exception.NotFoundException;
import com.example.CCINETApplication.model.Task;
import com.example.CCINETApplication.model.User;
import com.example.CCINETApplication.repository.TaskRepository;
import com.example.CCINETApplication.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/{userId}/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> listTasksForUser(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<Task> tasks = taskRepository.findByUser_Id(userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<?> createTaskForUser(@PathVariable Long userId, @RequestBody Task task) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = optionalUser.get();
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskInfo(@PathVariable Long userId, @PathVariable Long taskId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Optional<Task> optionalTask = taskRepository.findByIdAndUser_Id(taskId, userId);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        Task task = optionalTask.get();
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTaskForUser(@PathVariable Long userId, @PathVariable Long taskId, @RequestBody Task updatedTask) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Optional<Task> optionalTask = taskRepository.findByIdAndUser_Id(taskId, userId);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        Task existingTask = optionalTask.get();
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setStartDate(updatedTask.getStartDate());
        existingTask.setEndDate(updatedTask.getEndDate());
        existingTask.setDescription(updatedTask.getDescription());

        Task savedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTaskForUser(@PathVariable Long userId, @PathVariable Long taskId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        Optional<Task> optionalTask = taskRepository.findByIdAndUser_Id(taskId, userId);
        if (optionalTask.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }

        taskRepository.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }
}

