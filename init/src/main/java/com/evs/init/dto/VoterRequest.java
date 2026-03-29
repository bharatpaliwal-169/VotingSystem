package com.evs.init.dto;

import lombok.Data;

/**
 * DTO for registering as voter.
 */
@Data
public class VoterRequest {
    private String name;
    private String address;
    private String dateOfBirth;
}