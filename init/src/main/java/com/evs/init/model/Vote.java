package com.evs.init.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Vote entity represents a vote cast by a voter to a candidate.
 */
@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-one relationship with Voter.
     * A voter can cast multiple votes, but in a simple system, perhaps one per election.
     */
    @ManyToOne // Many votes can belong to one voter
    @JoinColumn(name = "voter_id", nullable = false)
    private Voter voter;

    /**
     * Many-to-one relationship with Candidate.
     */
    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    /**
     * Timestamp when the vote was cast.
     */
    @Column(nullable = false)
    private String timestamp; // Using String for simplicity
}