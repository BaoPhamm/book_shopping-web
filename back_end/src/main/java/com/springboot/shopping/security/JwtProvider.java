package com.springboot.shopping.security;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration.min}")
	private long expirationMin;

	@Value("${jwt.expiration.day}")
	private long expirationDay;

	// Create accessToken
	public String createToken(String username, List<String> roles) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		String accessToken = JWT.create().withSubject(username)
				// accessToken expires in 60 mins
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationMin * 60 * 1000))
				.withClaim("roles", roles).sign(algorithm);
		return accessToken;
	}

	// Create refreshToken
	public String createRefreshToken(String username) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		String refreshToken = JWT.create().withSubject(username)
				// refreshToken expires in 60 days
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationDay * 24 * 60 * 60 * 1000))
				.sign(algorithm);
		return refreshToken;
	}

	// Get token from Header
	public String getToken(String header) {
		return header.substring("Bearer ".length());
	}

	// Decode JWT
	public DecodedJWT decodeJWT(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
		JWTVerifier jwtVerifier = JWT.require(algorithm).build();
		return jwtVerifier.verify(token);
	}

	// Get username from header
	public String getUsername(String header) {
		String token = getToken(header);
		DecodedJWT decodedJWT = decodeJWT(token);
		return decodedJWT.getSubject();
	}

	// Get user's authorities from token
	public Collection<SimpleGrantedAuthority> getAuthorities(String header) {
		String token = getToken(header);
		DecodedJWT decodedJWT = decodeJWT(token);

		String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		stream(roles).forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role));
		});
		return authorities;
	}
}
