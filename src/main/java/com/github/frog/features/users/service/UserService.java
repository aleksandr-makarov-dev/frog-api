package com.github.frog.features.users.service;

import com.github.frog.features.users.entity.UserEntity;

public interface UserService {
    UserEntity getUserEntityByIdOrThrow(Long id);
}
