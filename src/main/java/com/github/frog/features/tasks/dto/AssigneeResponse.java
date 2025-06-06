package com.github.frog.features.tasks.dto;

import java.time.LocalDateTime;

public record AssigneeResponse(
        Long taskId,
        Long userId,
        LocalDateTime assignedAt
) {
}
