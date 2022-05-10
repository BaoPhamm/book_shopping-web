package com.springboot.shopping.dto.user;

import java.util.List;

import lombok.Data;

@Data
public class AddRoleToUserForm {
	private Long userId;
	private List<Long> rolesId;
}
