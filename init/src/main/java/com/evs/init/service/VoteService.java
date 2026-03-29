package com.evs.init.service;

import com.evs.init.model.Candidate;
import com.evs.init.model.Vote;
import com.evs.init.model.Voter;
import com.evs.init.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * VoteService handles voting logic.
 */
@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    /**
     * Casts a vote from a voter to a candidate.
     */
    public Vote castVote(Voter voter, Candidate candidate) {
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(candidate);
        vote.setTimestamp(LocalDateTime.now().toString()); // Simple timestamp
        return voteRepository.save(vote);
    }

    /**
     * Gets votes by voter.
     */
    public List<Vote> getVotesByVoter(Voter voter) {
        return voteRepository.findByVoter(voter);
    }

    /**
     * Gets votes by candidate.
     */
    public List<Vote> getVotesByCandidate(Candidate candidate) {
        return voteRepository.findByCandidate(candidate);
    }
}