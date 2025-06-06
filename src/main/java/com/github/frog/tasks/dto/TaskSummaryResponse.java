package com.github.frog.tasks.dto;

import com.github.frog.tasks.entity.TaskPriority;

import java.time.LocalDateTime;

public record TaskSummaryResponse(
        Long id,
        String name,
        TaskPriority priority,
        LocalDateTime dueDate
) {
}
