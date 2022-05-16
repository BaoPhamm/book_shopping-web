package com.springboot.shopping.dto.user;

import java.util.Objects;
import java.util.Set;

import com.springboot.shopping.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String phoneNumber;
	private String address;
	private boolean isBlocked;
	private Set<Role> roles;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResponse other = (UserResponse) obj;
		return Objects.equals(address, other.address) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && isBlocked == other.isBlocked
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(roles, other.roles) && Objects.equals(username, other.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, firstName, id, isBlocked, lastName, phoneNumber, roles, username);
	}

}