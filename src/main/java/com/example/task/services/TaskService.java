package com.example.task.services;

import com.example.task.exceptions.TaskNotFoundException;
import com.example.task.models.SearchRequest;
import com.example.task.models.Task;
import com.example.task.repository.TaskRepository;

import java.time.LocalDateTime;
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

    public Task create(Task task) {
        task.setCreated(LocalDateTime.now());
        return repository.save(task);
    }

    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task>tasks = repository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found");
        }
        return ResponseEntity.ok().body(tasks);
    }

    public ResponseEntity<Task> getSpecificTask(Long id) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            return ResponseEntity.ok().body(task.get());
        } else {
            throw new TaskNotFoundException(String.format(NO_TASKS_FOUND, id));
        }
    }

    public ResponseEntity<String> update(Long id, Task task) {
        repository.findById(id)
                  .ifPresentOrElse(existingTask -> {
                      existingTask.setTitle(task.getTitle());
                      existingTask.setDescription(task.getDescription());
                      existingTask.setUpdated(LocalDateTime.now());
                      repository.save(existingTask);
                  }, () -> {
                      throw new TaskNotFoundException(String.format(NO_TASKS_FOUND, id));
                  });
        return ResponseEntity.ok().body("Update was successful");
    }

    public ResponseEntity<String> deleteTask(Long id) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok("Task deleted successfully");
        }
        throw new TaskNotFoundException(String.format(NO_TASKS_FOUND, id));
    }

    public ResponseEntity<List<Task>> search(SearchRequest request) {
        List<Task> tasks = repository.findAll();
        String phrase = request.getPhrase();
        List<Task> retrievedTasks = tasks.stream().filter(task -> task.getTitle().contains(phrase)).toList();
        if (retrievedTasks.isEmpty()) {
            throw new TaskNotFoundException(String.format(String.format("No tasks found with phrase: %s", request.getPhrase())));
        }
        return ResponseEntity.ok().body(retrievedTasks);
    }
}
