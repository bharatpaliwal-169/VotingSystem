package com.evs.init.repository;

import com.evs.init.model.Candidate;
import com.evs.init.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Candidate entity.
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    /**
     * Find candidate by user.
     */
    Optional<Candidate> findByUser(User user);
}