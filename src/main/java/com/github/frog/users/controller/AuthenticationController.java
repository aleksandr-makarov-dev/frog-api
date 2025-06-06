package com.github.frog.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    @PostMapping("register")
    public ResponseEntity<?> registerUser() {
        throw new RuntimeException("Not implemented yet");
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser() {
        throw new RuntimeException("Not implemented yet");
    }
}
