package com.backend.backend.controller;

import com.backend.backend.model.User;
import com.backend.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // ✅ Corrected route: /users/me
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(Authentication authentication) {
        String username = authentication.getName();

        User user = userService.findByName(username);
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getName());
        response.put("message", "Login successful");

        return ResponseEntity.ok(response);
    }
}
