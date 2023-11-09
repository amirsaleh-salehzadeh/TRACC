package com.example.CCINETApplication.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.CCINETApplication.model.Task;

import java.util.Date;
import java.util.List;

@Service
public class TaskSchedulerService {

    private final TaskService taskService;

    @Autowired
    public TaskSchedulerService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 * * * * *") // Run every minute
    public void updatePendingTasks() {
        System.out.println("Scheduled job running...");

        // Get all tasks with status "pending" and overdue dates
        List<Task> pendingTasks = taskService.findPendingTasks();

        // Update tasks to "done"
        for (Task task : pendingTasks) {
            if (task.getEndDate().before(new Date())) {
                task.setStatus("DONE");
                taskService.saveTask(task);
                System.out.println("Task updated: " + task.getId());
            }
        }
    }
}
