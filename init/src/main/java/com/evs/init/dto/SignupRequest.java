package com.evs.init.dto;

import lombok.Data;

/**
 * DTO for signup request.
 */
@Data
public class SignupRequest {
    private String username;
    private String password;
    private String email;
}