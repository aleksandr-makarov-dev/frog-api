package com.github.frog.features.tasks.dto;

import jakarta.validation.constraints.NotNull;

public record AssigneeAddRequest(
        @NotNull(message = "UserId must be not empty")
        Long userId
) {
}
