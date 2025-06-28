package com.example.task.controllers;


import jakarta.validation.Valid;

import com.example.task.models.SearchRequest;
import com.example.task.models.Task;
import com.example.task.services.TaskService;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
    public ResponseEntity<String>createTask(@Valid @RequestBody Task task) {
        Task createdTask = taskService.create(task);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdTask.getId())
            .toUri();
        return ResponseEntity.created(location).body("Task created successfully");
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
    public ResponseEntity<String>updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        return taskService.update(id, task);
    }

    @DeleteMapping("/tasks/remove/{id}")
    public ResponseEntity<String>deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }

    @PostMapping("/tasks/search")
    public ResponseEntity<List<Task>>searchTask(@Valid @RequestBody SearchRequest request) {
        return taskService.search(request);
    }
}
