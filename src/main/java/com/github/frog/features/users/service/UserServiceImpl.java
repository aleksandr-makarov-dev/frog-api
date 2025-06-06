package com.github.frog.features.users.service;

import com.github.frog.features.users.entity.UserEntity;
import com.github.frog.features.users.exception.UserNotFoundException;
import com.github.frog.features.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserEntityByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with ID=%d not found".formatted(id)));
    }
}
