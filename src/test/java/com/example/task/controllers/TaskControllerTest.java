package com.example.task.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.example.task.helpers.TaskHelper.generateTask;
import com.example.task.models.ApiError;
import com.example.task.models.Task;
import com.example.task.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    
    @Test
    void givenPostCreatTaskEndpointIsCalled_whenAValidTaskIsProvided_thenA200IsReturned() throws Exception {
        Task task = generateTask();
        task.setId(1L);
        
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        
        String taskJson = mapper.writeValueAsString(task);

        mockMvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(taskJson))
               .andExpect(status().isCreated())
               .andExpect(content().string("Task created successfully"));
    }

    @Test
    void givenGetTasksEndpointIsCalled_whenThereAreMultipleTasksInTheDatabase_thenThereIsA200Response()
    throws Exception {
        Task firstTask = generateTask();
        firstTask.setId(1L);
        firstTask.setTitle("Task 1");
        firstTask.setDescription("Description 1");

        Task secondTask = generateTask();
        secondTask.setId(2L);
        secondTask.setTitle("Task 2");
        secondTask.setDescription("Description 2");

        when(taskRepository.findAll()).thenReturn(List.of(firstTask, secondTask));

        MvcResult result = mockMvc.perform(get("/api/tasks")).andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        List<Task> tasks = mapper.readValue(response, new TypeReference<>() {});
        assertEquals(2, tasks.size());
    }

    @Test
    void givenGetTasksEndpointIsCalled_whenThereAreNoTasksInTheDatabase_thenThereIsA404Response() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/tasks")).andExpect(status().isNotFound()).andReturn();

        ApiError apiError = mapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("No tasks found", apiError.getMessage());
    }

    @Test
    void givenGetTasksEndpointIsCalled_whenAnIdIsProvidedThereAreMultipleTasksInTheDatabase_thenTasksAreReturned()
    throws Exception {
        Task task = generateTask();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        MvcResult result = mockMvc
            .perform(get("/api/tasks/1"))
            .andExpect(status().isOk())
            .andReturn();

        String response = result.getResponse().getContentAsString();
        Task responseTask = mapper.readValue(response, Task.class);
        assertEquals(task.getId(), responseTask.getId());
    }

    @Test
    void givenGetTasksEndpointIsCalled_whenAnIdIsProvidedAndThereIsNoTaskInTheDatabase_thenNotFoundIsReturned()
    throws Exception {

        MvcResult result = mockMvc
            .perform(get("/api/tasks/1"))
            .andExpect(status().isNotFound())
            .andReturn();

        ApiError apiError = mapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("No tasks found with id: 1", apiError.getMessage());
    }

    @Test
    void givenGetTasksEndpointIsCalled_whenANonnumericalIsProvided_thenTasksAreReturned()
    throws Exception {
        MvcResult result = mockMvc
            .perform(get("/api/tasks/a"))
            .andExpect(status().isBadRequest())
            .andReturn();

        ApiError apiError = mapper.readValue(result.getResponse().getContentAsString(), ApiError.class);
        assertEquals("For input string: \"a\"", apiError.getMessage());
    }
}