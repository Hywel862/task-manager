package com.example.task.services;

import com.example.task.exceptions.TaskNotFoundException;
import com.example.task.models.SearchRequest;
import com.example.task.models.Task;
import com.example.task.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private static final String NO_TASKS_FOUND = "No tasks found with id: %s";
    @Autowired
    TaskRepository repository;

    public void create(Task task) {
        repository.save(task);
    }

    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task>tasks = repository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found");
        }
        return ResponseEntity.ok().body(repository.findAll());
    }

    public ResponseEntity<Task> getSpecificTask(Long id) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            return ResponseEntity.ok().body(task.get());
        } else {
            throw new TaskNotFoundException(String.format(NO_TASKS_FOUND, id));
        }
    }

    public ResponseEntity<String> update(String id, Task task) {
        Optional<Task> taskOptional =  repository.findById(Long.valueOf(id))
                         .map(updatedTask -> {
                             updatedTask.setDescription(task.getDescription());
                             updatedTask.setTitle(task.getTitle());
                             return repository.save(updatedTask);
                         });
        if (taskOptional.isPresent()) {
            return ResponseEntity.ok().body("Update was successful");
        }
        throw new TaskNotFoundException(String.format(NO_TASKS_FOUND, id));
    }

    public ResponseEntity<String> deleteTask(String id) {
        Optional<Task> task = repository.findById(Long.valueOf(id));
        if (task.isPresent()) {
            repository.deleteById(Long.valueOf(id));
            return ResponseEntity.ok("Task deleted successfully");
        }
        throw new TaskNotFoundException(String.format(NO_TASKS_FOUND, id));
    }

    public ResponseEntity<List<Task>> search(SearchRequest request) {
        List<Task> tasks = repository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException(String.format(String.format("No tasks found with phrase: %s", request.getPhrase())));
        }
        request.setPhrase(request.getPhrase().toLowerCase());
        tasks.removeIf(task -> !task.getTitle().toLowerCase().contains(request.getPhrase()));
        return ResponseEntity.ok().body(tasks);
    }
}
