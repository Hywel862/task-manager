package com.example.task.repository;

import com.example.task.models.Task;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{
    Optional<Task> findById(Long id);
}
