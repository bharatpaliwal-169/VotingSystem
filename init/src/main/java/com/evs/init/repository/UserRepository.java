package com.evs.init.repository;

import com.evs.init.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository interface extends JpaRepository to provide CRUD operations for User entity.
 * JpaRepository provides methods like save, findById, findAll, delete, etc.
 */
@Repository // Marks this interface as a Spring Data repository, enabling component scanning and dependency injection
public interface UserRepository extends JpaRepository<User, Long> { // JpaRepository<User, Long> where User is the entity type and Long is the ID type

    /**
     * Custom method to find a user by username.
     * Spring Data JPA will automatically generate the implementation based on the method name.
     */
    Optional<User> findByUsername(String username);

    /**
     * Custom method to find a user by email.
     */
    Optional<User> findByEmail(String email);
}