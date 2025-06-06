package com.github.frog.tasks.service;

import com.github.frog.tasks.dto.TaskCreateRequest;
import com.github.frog.tasks.dto.TaskDetailsResponse;
import com.github.frog.tasks.dto.TaskSummaryResponse;
import com.github.frog.tasks.dto.TaskUpdateRequest;

import java.util.List;

public interface TaskService {

    TaskDetailsResponse createTask(TaskCreateRequest request);

    List<TaskSummaryResponse> getAllTasks();

    TaskDetailsResponse getTaskById(Long id);

    TaskDetailsResponse updateTaskById(Long id, TaskUpdateRequest request);

    void deleteTaskById(Long id);
}
