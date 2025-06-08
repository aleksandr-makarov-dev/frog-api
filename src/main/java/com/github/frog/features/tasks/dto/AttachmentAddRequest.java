package com.github.frog.features.tasks.dto;

import jakarta.validation.constraints.NotNull;

public record AttachmentAddRequest(
        @NotNull(message = "ResourceId must be not empty")
        Long resourceId
) {
}
