package com.springboot.shopping.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Don't check Authorization for request to login and refresh token
		if (request.getServletPath().equals("/api/v1/auth/login")
				|| request.getServletPath().equals("/api/v1/user/refresh")) {
			filterChain.doFilter(request, response);
		} else {
			// Get request Header
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					// Get username from header
					String username = jwtProvider.getUsername(authorizationHeader);
					// Get user's authorities from header
					Collection<SimpleGrantedAuthority> authorities = jwtProvider.getAuthorities(authorizationHeader);

					// Create and save authenticationToken to SecurityContextHolder
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
