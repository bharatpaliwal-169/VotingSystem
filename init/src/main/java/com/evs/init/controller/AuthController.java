package com.evs.init.controller;

import com.evs.init.dto.LoginRequest;
import com.evs.init.dto.SignupRequest;
import com.evs.init.model.User;
import com.evs.init.service.UserService;
import com.evs.init.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthController handles authentication-related endpoints.
 */
@RestController // Marks this class as a REST controller, combining @Controller and @ResponseBody
@RequestMapping("/api/auth") // Base URL for all endpoints in this controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint for user signup.
     * @PostMapping maps HTTP POST requests to this method
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) { // @RequestBody binds the HTTP request body to the method parameter
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            User savedUser = userService.registerUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Endpoint for user login.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (userService.authenticate(request.getUsername(), request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}