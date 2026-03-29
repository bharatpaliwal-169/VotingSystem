package com.evs.init.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Voter entity represents a voter in the voting system.
 * A voter is a user who has registered to vote.
 */
@Entity
@Table(name = "voters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with User.
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Full name of the voter.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Address of the voter.
     */
    @Column(nullable = false)
    private String address;

    /**
     * Date of birth for age verification.
     */
    @Column(nullable = false)
    private String dateOfBirth; // Using String for simplicity, in real app use LocalDate
}