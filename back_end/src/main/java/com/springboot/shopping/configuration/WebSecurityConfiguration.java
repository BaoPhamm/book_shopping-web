package com.springboot.shopping.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.shopping.security.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final CustomAuthorizationFilter customAuthorizationFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Enable cors
		http.cors();
		// Disable Cross-Site Request Forgery
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Without Authorization
		http.authorizeRequests().antMatchers(
				"/api/v1/auth/login",
				"/api/v1/registration",
				"/api/v1/books/**",
				"/api/v1/category/**",
				"/swagger-ui.html",
				"/swagger-ui/**",
				"/v3/api-docs/**"
				).permitAll();

		// User Authorization
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/user/**").hasAnyAuthority("USER");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/user/**").hasAnyAuthority("USER");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/user/**").hasAnyAuthority("USER");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasAnyAuthority("USER");

		// Admin Authorization
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/admin/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasAnyAuthority("ADMIN");
		http.authorizeRequests().anyRequest().fullyAuthenticated();

		// Add AuthorizationFilter
		http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();

	}

}