package com.springboot.shopping.dto.auth;

import lombok.Data;

@Data
public class AuthenticationResponse {
	
    private String username;
    private String token;
    private String refreshToken;
    private String userRoles;
}
