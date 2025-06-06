package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.dto.TaskCreateRequest;
import com.github.frog.features.tasks.dto.TaskDetailsResponse;
import com.github.frog.features.tasks.dto.TaskSummaryResponse;
import com.github.frog.features.tasks.dto.TaskUpdateRequest;
import com.github.frog.features.tasks.entity.TaskEntity;

import java.util.List;

public interface TaskService {

    TaskDetailsResponse createTask(TaskCreateRequest request);

    List<TaskSummaryResponse> getAllTasks();

    TaskDetailsResponse getTaskById(Long id);

    TaskDetailsResponse updateTaskById(Long id, TaskUpdateRequest request);

    void deleteTaskById(Long id);

    TaskEntity getTaskEntityByIdOrThrow(Long id);
}
