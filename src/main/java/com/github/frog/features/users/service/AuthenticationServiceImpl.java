package com.github.frog.features.users.service;

import com.github.frog.features.users.dto.UserRegisterRequest;
import com.github.frog.features.users.dto.UserRegisterResponse;
import com.github.frog.features.users.entity.UserEntity;
import com.github.frog.features.users.exception.UserAlreadyExistsException;
import com.github.frog.features.users.mapper.UserMapper;
import com.github.frog.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(request.email());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("A user with the EMAIL=%s already exists.".formatted(request.email()));
        }

        UserEntity user = userMapper.toUserEntity(request);
        user.setPasswordHash(request.password());
        user.setEmailConfirmed(true);

        return userMapper.toUserRegisterResponse(userRepository.save(user));
    }
}
