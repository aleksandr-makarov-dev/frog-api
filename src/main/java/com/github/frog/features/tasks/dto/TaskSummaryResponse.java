package com.github.frog.features.tasks.dto;

import com.github.frog.features.tasks.entity.TaskPriority;

import java.time.LocalDateTime;

public record TaskSummaryResponse(
        Long id,
        String name,
        TaskPriority priority,
        LocalDateTime dueDate
) {
}
