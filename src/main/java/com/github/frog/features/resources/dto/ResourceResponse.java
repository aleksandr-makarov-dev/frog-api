package com.github.frog.features.resources.dto;

import java.time.LocalDateTime;

public record ResourceResponse(
        Long id,
        String originalName,
        String extension,
        Long size,
        String mimeType,
        String downloadUrl
) {
}
