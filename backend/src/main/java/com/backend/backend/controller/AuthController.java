package com.backend.backend.controller;

import com.backend.backend.model.User;
import com.backend.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByName(user.getName()) != null) {
            return "Username already exists!";
        }

        userRepository.save(user); // No password encoding here for testing
        return "Registered successfully";
    }

    @PostMapping("/logout")
    public String logout() {
        return "Logged out (stateless) — frontend should clear credentials.";
    }
}
