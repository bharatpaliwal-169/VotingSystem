package com.evs.init.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Candidate entity represents a candidate in the voting system.
 * A candidate is a user who has registered to run in elections.
 */
@Entity // JPA entity
@Table(name = "candidates") // Table name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with User.
     * Each candidate is associated with exactly one user.
     */
    @OneToOne // Specifies a one-to-one relationship
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;

    /**
     * Full name of the candidate.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Political party of the candidate.
     */
    @Column(nullable = false)
    private String party;

    /**
     * Description or manifesto of the candidate.
     */
    @Column(length = 1000) // Specifies column length
    private String description;
}