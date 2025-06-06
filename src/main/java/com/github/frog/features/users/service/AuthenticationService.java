package com.github.frog.features.users.service;

import com.github.frog.features.users.dto.UserRegisterRequest;
import com.github.frog.features.users.dto.UserRegisterResponse;

public interface AuthenticationService {

    UserRegisterResponse registerUser(UserRegisterRequest request);

}
