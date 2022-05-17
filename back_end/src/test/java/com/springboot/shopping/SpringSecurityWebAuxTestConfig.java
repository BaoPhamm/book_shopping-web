package com.springboot.shopping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

	@Bean
	@Primary
	public UserDetailsService userDetailsService() {

		Collection<SimpleGrantedAuthority> userAuthorities = new ArrayList<>();
		userAuthorities.add(new SimpleGrantedAuthority("USER"));

		Collection<SimpleGrantedAuthority> adminAuthorities = new ArrayList<>();
		adminAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

		User user = new User("user", "password", userAuthorities);
		User admin = new User("admin", "password", adminAuthorities);

		return new InMemoryUserDetailsManager(Arrays.asList(user, admin));

	}
}
