package com.github.frog.features.tasks;

import com.github.frog.features.tasks.dto.TaskCreateRequest;
import com.github.frog.features.tasks.dto.TaskUpdateRequest;
import com.github.frog.features.tasks.entity.AssigneeEntity;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.entity.TaskPriority;
import com.github.frog.features.users.entity.UserEntity;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class TaskTestData {

    public static List<TaskEntity> mockTaskEntityList(UserEntity createdBy) {
        return List.of(
                TaskEntity.builder()
                        .name("task 1")
                        .description("task 1 description")
                        .priority(TaskPriority.MEDIUM)
                        .createdBy(createdBy)
                        .createdAt(LocalDateTime.now())
                        .dueDate(LocalDateTime.of(2019, Month.APRIL, 2, 17, 0))
                        .build(),
                TaskEntity.builder()
                        .name("task 2")
                        .description("task 2 description")
                        .priority(TaskPriority.LOW)
                        .createdBy(createdBy)
                        .createdAt(LocalDateTime.now())
                        .dueDate(LocalDateTime.of(2025, Month.DECEMBER, 2, 17, 0))
                        .build(),
                TaskEntity.builder()
                        .name("task 3")
                        .description("task 3 description")
                        .priority(TaskPriority.HIGH)
                        .createdBy(createdBy)
                        .createdAt(LocalDateTime.now())
                        .dueDate(LocalDateTime.of(2019, Month.APRIL, 2, 17, 0))
                        .build(),
                TaskEntity.builder()
                        .name("task 4")
                        .description("task 4 description")
                        .priority(TaskPriority.CRITICAL)
                        .createdBy(createdBy)
                        .createdAt(LocalDateTime.now())
                        .dueDate(LocalDateTime.of(2019, Month.APRIL, 2, 17, 0))
                        .build()
        );
    }

    public static TaskEntity mockTaskEntity(UserEntity createdBy) {
        return TaskEntity.builder()
                .name("task 1")
                .description("task 1 description")
                .createdBy(createdBy)
                .priority(TaskPriority.LOW)
                .createdAt(LocalDateTime.of(2025, 6, 7, 12, 45))
                .build();
    }

    public static TaskCreateRequest mockTaskCreateRequest(Long createdById) {
        return new TaskCreateRequest(
                "test task name",
                "test task description",
                TaskPriority.MEDIUM,
                LocalDateTime.of(2025, 6, 7, 12, 45),
                createdById);
    }

    public static TaskUpdateRequest mockTaskUpdateRequest() {
        return new TaskUpdateRequest(
                "task updated name",
                "task updated description",
                TaskPriority.HIGH,
                LocalDateTime.of(2026, 8, 12, 14, 15)
        );
    }

    public static AssigneeEntity mockAssigneeEntity(TaskEntity task, UserEntity user) {
        AssigneeEntity assignee = new AssigneeEntity();
        assignee.setAssignedAt(LocalDateTime.now());
        task.addAssignee(assignee);
        user.addAssignee(assignee);

        return assignee;
    }
}
