package com.example.CCINETApplication;
import com.example.CCINETApplication.controller.TaskController;
import com.example.CCINETApplication.model.Task;
import com.example.CCINETApplication.model.User;
import com.example.CCINETApplication.repository.TaskRepository;
import com.example.CCINETApplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskController taskController;

    @Test
    void testListTasksForUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(taskRepository.findByUser_Id(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = taskController.listTasksForUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
    }

    @Test
    void testCreateTaskForUser() {
        Long userId = 1L;
        Long taskId = 1L;
        Task task = new Task();
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any())).thenReturn(task);

        ResponseEntity<?> response = taskController.createTaskForUser(userId, task);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Task);
    }

    @Test
    void testGetTaskInfo() {
        Long userId = 1L;
        Long taskId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(taskRepository.findByIdAndUser_Id(taskId, userId)).thenReturn(Optional.of(new Task()));

        ResponseEntity<?> response = taskController.getTaskInfo(userId, taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Task);
    }

    @Test
    void testUpdateTaskForUser() {
        Long userId = 1L;
        Long taskId = 1L;
        Task updatedTask = new Task();

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(taskRepository.findByIdAndUser_Id(taskId, userId)).thenReturn(Optional.of(new Task()));
        when(taskRepository.save(any())).thenReturn(updatedTask);

        ResponseEntity<?> response = taskController.updateTaskForUser(userId, taskId, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Task);
    }

    @Test
    void testDeleteTaskForUser() {
        Long userId = 1L;
        Long taskId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(taskRepository.findByIdAndUser_Id(taskId, userId)).thenReturn(Optional.of(new Task()));

        ResponseEntity<?> response = taskController.deleteTaskForUser(userId, taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
