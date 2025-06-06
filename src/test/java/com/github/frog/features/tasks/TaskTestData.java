package com.github.frog.features.tasks;

import com.github.frog.features.tasks.dto.TaskCreateRequest;
import com.github.frog.features.tasks.dto.TaskUpdateRequest;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.entity.TaskPriority;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TaskTestData {

    public static List<TaskEntity> mockTaskEntityList() {
        return List.of(
                new TaskEntity("task 1", "task 1 description", LocalDateTime.now(), LocalDateTime.of(2025, 6, 7, 12, 45), TaskPriority.LOW, Collections.emptyList()),
                new TaskEntity("task 2", "task 2 description", LocalDateTime.now(), LocalDateTime.of(2025, 6, 8, 13, 45), TaskPriority.LOW, Collections.emptyList()),
                new TaskEntity("task 3", "task 3 description", LocalDateTime.now(), null, TaskPriority.LOW, Collections.emptyList()),
                new TaskEntity("task 4", "task 4 description", LocalDateTime.now(), LocalDateTime.of(2025, 6, 10, 16, 30), TaskPriority.LOW, Collections.emptyList())
        );
    }

    public static TaskEntity mockTaskEntity() {
        return new TaskEntity("task 1", "task 1 description", LocalDateTime.now(), LocalDateTime.of(2025, 6, 7, 12, 45), TaskPriority.LOW, Collections.emptyList());
    }

    public static TaskCreateRequest mockTaskCreateRequest() {
        return new TaskCreateRequest(
                "test task name",
                "test task description",
                TaskPriority.MEDIUM,
                LocalDateTime.of(2025, 6, 7, 12, 45));
    }

    public static TaskUpdateRequest mockTaskUpdateRequest() {
        return new TaskUpdateRequest(
                "task updated name",
                "task updated description",
                TaskPriority.HIGH,
                LocalDateTime.of(2026, 8, 12, 14, 15)
        );
    }

}
