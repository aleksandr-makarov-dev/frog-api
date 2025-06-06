package com.github.frog.features.tasks.dto;

import com.github.frog.features.tasks.entity.TaskPriority;

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
