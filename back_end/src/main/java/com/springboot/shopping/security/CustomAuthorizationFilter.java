package com.springboot.shopping.security;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final String secretKey;

	public CustomAuthorizationFilter(String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/api/v1/login")
				|| request.getServletPath().equals("/api/v1/token/refresh")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
					JWTVerifier jwtVerifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = jwtVerifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					stream(roles).forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role));
					});
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);

				} catch (Exception e) {
					response.setHeader("Header", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());

					Map<String, String> errors = new HashMap<String, String>();
					errors.put("Error_message", e.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), errors);
				}

			} else {
				filterChain.doFilter(request, response);
			}
		}

	}

}