package com.evs.init.controller;

import com.evs.init.dto.CandidateRequest;
import com.evs.init.model.Candidate;
import com.evs.init.service.CandidateService;
import com.evs.init.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CandidateController handles candidate-related operations.
 */
@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registers a user as a candidate.
     * Requires JWT token in Authorization header.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAsCandidate(@RequestHeader("Authorization") String token, @RequestBody CandidateRequest request) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7)); // Remove "Bearer "
            Candidate candidate = new Candidate();
            candidate.setName(request.getName());
            candidate.setParty(request.getParty());
            candidate.setDescription(request.getDescription());
            Candidate saved = candidateService.registerAsCandidate(username, candidate);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    /**
     * Gets all candidates.
     */
    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        return ResponseEntity.ok(candidateService.getAllCandidates());
    }
}