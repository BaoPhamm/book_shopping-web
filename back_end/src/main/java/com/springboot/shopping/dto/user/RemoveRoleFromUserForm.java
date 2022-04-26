package com.springboot.shopping.dto.user;

import lombok.Data;

@Data
public class RemoveRoleFromUserForm {
	private String username;
	private String rolename;
}
