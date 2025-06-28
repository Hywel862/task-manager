package com.example.task.services;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.example.task.exceptions.TaskNotFoundException;
import static com.example.task.helpers.TaskHelper.generateTask;
import com.example.task.models.Task;
import com.example.task.repository.TaskRepository;

@SpringBootTest
class TaskServiceTest {
    @MockBean
    TaskRepository taskRepository;
    @Autowired
    TaskService taskService;

    @Test
    void givenCreateIsCalled_whenATaskIsPassed_thenTheResponseEntityReturnedIs201(){
        Task task = generateTask();

        when(taskRepository.save(task)).thenReturn(task);

        assertEquals(task.getId(), taskService.create(task).getId());
        assertEquals(task.getDescription(), taskService.create(task).getDescription());
        assertEquals(task.getTitle(), taskService.create(task).getTitle());
    }

    @Test
    void givenGetAllTasksIsCalled_whenMultipleTestsAreInTheDatabase_thenTheResponseEntityReturnedIs200(){
        Task task = generateTask();

        when(taskRepository.findAll()).thenReturn(List.of(task));

        assertEquals(HttpStatus.OK, taskService.getAllTasks().getStatusCode());
    }

    @Test
    void givenGetAllTasksIsCalled_whenThereAreNoTasksInTheDatabase_thenTaskNotFoundExceptionIsThrown() {
        when(taskRepository.findAll()).thenReturn(List.of());

        assertThrows(TaskNotFoundException.class, () -> taskService.getAllTasks());
    }

    @Test
    void givenGetSpecificTaskIsCalled_whenThereIsAMatchingIdInTheDatabase_thenTheResponseEntityReturnedIs200() {
        Task task = generateTask();
        task.setId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        assertEquals(HttpStatus.OK, taskService.getSpecificTask(1L).getStatusCode());
    }

    @Test
    void givenGetSpecificTaskIsCalled_whenThereIsNoMatchingIdInTheDatabase_thenTaskNotFoundExceptionIsThrown() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getSpecificTask(1L));
    }
}