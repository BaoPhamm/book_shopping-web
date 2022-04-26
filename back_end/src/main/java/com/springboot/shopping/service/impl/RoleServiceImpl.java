package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.exception.role.RoleExistException;
import com.springboot.shopping.exception.role.RoleNotFoundException;
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
			throw new RoleExistException();
		}
		return commonMapper.convertToResponse(roleRepository.save(roleFromDb.get()), RoleResponse.class);
	}

	@Override
	public List<RoleResponse> deleteRole(String roleName) {
		Optional<Role> roleFromDb = roleRepository.findByname(roleName);
		if (roleFromDb.isEmpty()) {
			throw new RoleNotFoundException();
		}
		roleRepository.delete(roleFromDb.get());
		return commonMapper.convertToResponseList(roleRepository.findAll(), RoleResponse.class);
	}

}
