package com.github.frog.features.users;

import com.github.frog.features.users.entity.UserEntity;

import java.time.LocalDateTime;

public class UserTestData {

    public static UserEntity mockUserEntity() {
        return UserEntity.builder()
                .email("testuser@example.com")
                .passwordHash("123456789")
                .createdAt(LocalDateTime.now())
                .emailConfirmed(true)
                .accessFailedCount(0)
                .build();
    }
}
