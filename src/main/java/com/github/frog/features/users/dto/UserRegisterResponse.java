package com.github.frog.features.users.dto;

import java.time.LocalDateTime;

public record UserRegisterResponse(
        String email,
        LocalDateTime createdAt
) {
}
