package com.github.frog.features.users.mapper;

import com.github.frog.features.users.dto.UserRegisterRequest;
import com.github.frog.features.users.dto.UserRegisterResponse;
import com.github.frog.features.users.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public UserEntity toUserEntity(UserRegisterRequest request) {
        return UserEntity.builder()
                .email(request.email())
                .createdAt(LocalDateTime.now())
                .accessFailedCount(0)
                .build();
    }

    public UserRegisterResponse toUserRegisterResponse(UserEntity entity) {
        return new UserRegisterResponse(entity.getEmail(), entity.getCreatedAt());
    }
}
