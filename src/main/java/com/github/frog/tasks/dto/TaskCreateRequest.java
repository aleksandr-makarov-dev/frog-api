package com.github.frog.tasks.dto;

import com.github.frog.tasks.entity.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskCreateRequest(
        @NotBlank(message = "Task name must not be blank")
        @Size(min = 5, max = 150, message = "Task name must be between {min} and {max} characters")
        String name,

        @Size(min = 5, max = 250, message = "Task description must be between {min} and {max} characters")
        String description,

        @NotNull(message = "Task priority is required")
        TaskPriority priority,

        @FutureOrPresent(message = "Due date must be in the present or future")
        LocalDateTime dueDate
) {
}


