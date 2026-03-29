package com.evs.init.repository;

import com.evs.init.model.User;
import com.evs.init.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Voter entity.
 */
@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {

    /**
     * Find voter by user.
     */
    Optional<Voter> findByUser(User user);
}