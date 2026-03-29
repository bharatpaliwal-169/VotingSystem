package com.evs.init.dto;

import lombok.Data;

/**
 * DTO for registering as candidate.
 */
@Data
public class CandidateRequest {
    private String name;
    private String party;
    private String description;
}