package com.springboot.shopping.controller.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.shopping.SpringSecurityWebAuxTestConfig;
import com.springboot.shopping.dto.PasswordResetRequest;
import com.springboot.shopping.dto.user.UserRequest;
import com.springboot.shopping.dto.user.UserResponse;
import com.springboot.shopping.service.UserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringSecurityWebAuxTestConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private UserService userService;

	private Authentication authenticationToken;

	@BeforeEach
	void beforeEach() {
		Collection<SimpleGrantedAuthority> userAuthorities = new ArrayList<>();
		userAuthorities.add(new SimpleGrantedAuthority("USER"));
		authenticationToken = new UsernamePasswordAuthenticationToken("user", null, userAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

	// UnitTest for function getUserInfo()
	@Test
	@WithUserDetails("user")
	void getUserInfo_ShouldReturnUserInfo() throws Exception {

		UserResponse userResponse = UserResponse.builder().id(1L).firstName("firstname").build();

		when(userService.findUserByUsername("user")).thenReturn(userResponse);

		mockMvc.perform(get("/api/v1/user/info")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(Integer.valueOf(userResponse.getId().intValue()))))
				.andExpect(jsonPath("$.firstName", equalTo(userResponse.getFirstName())));
	}

	// UnitTest for function updateProfile()
	@Test
	@WithUserDetails("user")
	void updateUserInfo_ShouldReturnUpdatedUser() throws Exception {

		UserRequest userRequest = UserRequest.builder().firstName("firstName").lastName("lastName")
				.phoneNumber("phoneNumber").address("address").build();

		UserResponse userResponse = UserResponse.builder().id(1L).firstName("firstname").build();

		when(userService.updateProfile("user", userRequest)).thenReturn(userResponse);

		mockMvc.perform(put("/api/v1/user/info").content(mapper.writeValueAsString(userRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(Integer.valueOf(userResponse.getId().intValue()))))
				.andExpect(jsonPath("$.firstName", equalTo(userResponse.getFirstName())));
	}

	// UnitTest for function updateUserPassword()
	@Test
	@WithUserDetails("user")
	void updateUserPassword_ShouldReturnUpdatedUser() throws Exception {

		PasswordResetRequest passwordResetRequest = PasswordResetRequest.builder().currentPassword("currentPassword")
				.newPassword("newPassword").newPasswordRepeat("newPassword").build();

		String messageResponse = "Success";

		when(userService.passwordReset("user", passwordResetRequest)).thenReturn(messageResponse);
		MvcResult mvcResult = mockMvc.perform(put("/api/v1/user/password")
				.content(mapper.writeValueAsString(passwordResetRequest)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		assertThat(content, is(messageResponse));
	}

//	@Test
//	void testUpdateUserInfo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateUserPassword() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRefreshToken() {
//		fail("Not yet implemented");
//	}

}
