package com.springboot.shopping.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.exception.RoleExistException;
import com.springboot.shopping.mapper.CommonMapper;
import com.springboot.shopping.model.Role;
import com.springboot.shopping.repository.RoleRepository;
import com.springboot.shopping.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	private final CommonMapper commonMapper;

	@Override
	public RoleResponse createRole(String roleName) {

		Optional<Role> roleFromDb = roleRepository.findByname(roleName);
		if (roleFromDb.isPresent()) {
			throw new RoleExistException("Role is already exist.");
		}
		return commonMapper.convertToResponse(roleFromDb, RoleResponse.class);
	}

}
