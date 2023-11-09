package com.example.CCINETApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CCINETApplication.model.Task;
import com.example.CCINETApplication.repository.TaskRepository;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findPendingTasks() {
        return taskRepository.findByStatusAndEndDateBefore("PENDING", new Date());
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}
