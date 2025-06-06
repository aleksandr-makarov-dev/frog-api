package com.github.frog.tasks.dto;

import com.github.frog.tasks.entity.TaskPriority;

import java.time.LocalDateTime;

public record TaskDetailsResponse(
        Long id,
        String name,
        String description,
        TaskPriority priority,
        LocalDateTime createdAt,
        LocalDateTime dueDate
) {
}
