package com.evs.init.dto;

import lombok.Data;

/**
 * DTO for casting a vote.
 */
@Data
public class VoteRequest {
    private Long candidateId;
}