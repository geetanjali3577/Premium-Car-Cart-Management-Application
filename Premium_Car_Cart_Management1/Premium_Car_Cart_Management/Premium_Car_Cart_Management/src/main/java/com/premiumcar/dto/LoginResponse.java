package com.premiumcar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String message;
    private Long userId;
    private String name;
    private String email;
    private String role;
}
