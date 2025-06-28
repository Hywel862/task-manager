package com.example.task.helpers;

import com.example.task.models.Task;

public class TaskHelper {
    public static Task generateTask() {
        Task task = new Task();
        task.setTitle("Test title");
        task.setDescription("Test description");
        return task;
    }
}
