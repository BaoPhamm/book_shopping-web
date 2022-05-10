package com.springboot.shopping.dto.user;

import java.util.List;

import lombok.Data;

@Data
public class RemoveRoleFromUserForm {
	private Long userId;
	private List<Long> rolesId;
}
