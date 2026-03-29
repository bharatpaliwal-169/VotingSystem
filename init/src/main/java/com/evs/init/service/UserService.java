package com.evs.init.service;

import com.evs.init.model.User;
import com.evs.init.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserService handles business logic related to users.
 * It provides methods for user registration, authentication, etc.
 */
@Service // Marks this class as a Spring service component, enabling dependency injection and component scanning
public class UserService {

    @Autowired // Injects the UserRepository dependency
    private UserRepository userRepository;

    @Autowired // Injects the PasswordEncoder for encoding passwords
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user.
     * Encodes the password before saving.
     */
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
        user.setRole(User.Role.USER); // Default role
        return userRepository.save(user); // Save and return the user
    }

    /**
     * Finds a user by username.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user by email.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Authenticates a user by username and password.
     */
    public boolean authenticate(String username, String password) {
        Optional<User> user = findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
}