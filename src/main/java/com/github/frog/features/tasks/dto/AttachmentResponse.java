package com.github.frog.features.tasks.dto;

import java.time.LocalDateTime;

public record AttachmentResponse(
        Long id,
        LocalDateTime attachedAt
) {
}
