package com.evs.init.service;

import com.evs.init.model.Candidate;
import com.evs.init.model.User;
import com.evs.init.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CandidateService handles business logic for candidates.
 */
@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private UserService userService;

    /**
     * Registers a user as a candidate.
     */
    public Candidate registerAsCandidate(String username, Candidate candidate) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            candidate.setUser(user.get());
            user.get().setRole(User.Role.CANDIDATE); // Update role
            userService.userRepository.save(user.get()); // Save updated user
            return candidateRepository.save(candidate);
        }
        throw new RuntimeException("User not found");
    }

    /**
     * Gets all candidates.
     */
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    /**
     * Finds candidate by user.
     */
    public Optional<Candidate> findByUser(User user) {
        return candidateRepository.findByUser(user);
    }
}