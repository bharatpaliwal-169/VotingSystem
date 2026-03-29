package com.evs.init.service;

import com.evs.init.model.User;
import com.evs.init.model.Voter;
import com.evs.init.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * VoterService handles business logic for voters.
 */
@Service
public class VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private UserService userService;

    /**
     * Registers a user as a voter.
     */
    public Voter registerAsVoter(String username, Voter voter) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            voter.setUser(user.get());
            user.get().setRole(User.Role.VOTER);
            userService.userRepository.save(user.get());
            return voterRepository.save(voter);
        }
        throw new RuntimeException("User not found");
    }

    /**
     * Finds voter by user.
     */
    public Optional<Voter> findByUser(User user) {
        return voterRepository.findByUser(user);
    }
}