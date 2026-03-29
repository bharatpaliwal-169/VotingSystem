package com.evs.init.controller;

import com.evs.init.dto.VoteRequest;
import com.evs.init.model.Candidate;
import com.evs.init.model.Vote;
import com.evs.init.model.Voter;
import com.evs.init.service.CandidateService;
import com.evs.init.service.VoteService;
import com.evs.init.service.VoterService;
import com.evs.init.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * VoteController handles voting operations.
 */
@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoterService voterService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Casts a vote.
     */
    @PostMapping("/cast")
    public ResponseEntity<?> castVote(@RequestHeader("Authorization") String token, @RequestBody VoteRequest request) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Voter voter = voterService.findByUser(voterService.userService.findByUsername(username).get()).orElseThrow(() -> new RuntimeException("Voter not found"));
            Candidate candidate = candidateService.getAllCandidates().stream().filter(c -> c.getId().equals(request.getCandidateId())).findFirst().orElseThrow(() -> new RuntimeException("Candidate not found"));
            Vote vote = voteService.castVote(voter, candidate);
            return ResponseEntity.ok(vote);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}