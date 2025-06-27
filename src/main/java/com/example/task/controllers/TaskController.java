package com.example.task.controllers;


import com.example.task.models.SearchRequest;
import com.example.task.models.Task;
import com.example.task.services.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<String>createTask(@RequestBody Task task) {
        taskService.create(task);
        return ResponseEntity.ok("Task created successfully");
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>>getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task>getTaskById(@PathVariable Long id) {
        return taskService.getSpecificTask(id);
    }

    @PutMapping ("/tasks/update/{id}")
    public ResponseEntity<String>updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.update(id, task);
    }

    @DeleteMapping("/tasks/remove/{id}")
    public ResponseEntity<String>deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/tasks/search")
    public ResponseEntity<List<Task>>searchTask(@RequestBody SearchRequest request) {
        return taskService.search(request);
    }
}
