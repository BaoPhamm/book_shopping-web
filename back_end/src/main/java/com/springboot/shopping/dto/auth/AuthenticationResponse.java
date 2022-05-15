package com.springboot.shopping.dto.auth;

import java.util.Objects;

import lombok.Data;

@Data
public class AuthenticationResponse {

	private String username;
	private String token;
	private String refreshToken;
	private String userRoles;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationResponse other = (AuthenticationResponse) obj;
		return Objects.equals(refreshToken, other.refreshToken) && Objects.equals(token, other.token)
				&& Objects.equals(userRoles, other.userRoles) && Objects.equals(username, other.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(refreshToken, token, userRoles, username);
	}

}
