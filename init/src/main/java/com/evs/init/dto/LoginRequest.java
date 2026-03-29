package com.evs.init.dto;

import lombok.Data;

/**
 * DTO for login request.
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}