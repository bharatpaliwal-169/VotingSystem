package com.evs.init.controller;

import com.evs.init.dto.VoterRequest;
import com.evs.init.model.Voter;
import com.evs.init.service.VoterService;
import com.evs.init.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * VoterController handles voter registration.
 */
@RestController
@RequestMapping("/api/voters")
public class VoterController {

    @Autowired
    private VoterService voterService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registers a user as a voter.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerAsVoter(@RequestHeader("Authorization") String token, @RequestBody VoterRequest request) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Voter voter = new Voter();
            voter.setName(request.getName());
            voter.setAddress(request.getAddress());
            voter.setDateOfBirth(request.getDateOfBirth());
            Voter saved = voterService.registerAsVoter(username, voter);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}