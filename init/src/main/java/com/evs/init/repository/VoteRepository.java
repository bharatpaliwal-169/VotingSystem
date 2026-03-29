package com.evs.init.repository;

import com.evs.init.model.Candidate;
import com.evs.init.model.Vote;
import com.evs.init.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Vote entity.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Find votes by voter.
     */
    List<Vote> findByVoter(Voter voter);

    /**
     * Find votes by candidate.
     */
    List<Vote> findByCandidate(Candidate candidate);
}