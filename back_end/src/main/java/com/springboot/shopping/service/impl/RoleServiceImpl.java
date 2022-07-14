package com.springboot.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.shopping.dto.role.RoleResponse;
import com.springboot.shopping.exception.role.DeleteDefaultRoleException;
import com.springboot.shopping.exception.role.RoleExistException;
import com.springboot.shopping.exception.role.RoleNotFoundException;
import com.springboot.shopping.exception.role.UpdateDefaultRoleException;
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
	public RoleResponse findRoleByName(String roleName) {
		Role roleFromDb = roleRepository.findByname(roleName).orElseThrow(() -> new RoleNotFoundException());
		return commonMapper.convertToResponse(roleFromDb, RoleResponse.class);
	}

	@Override
	public List<RoleResponse> getAllRoles() {
		List<Role> allRoles = roleRepository.findAll();
		return commonMapper.convertToResponseList(allRoles, RoleResponse.class);
	}

	@Override
	public RoleResponse createRole(String roleName) {
		Optional<Role> roleFromDb = roleRepository.findByname(roleName);
		if (roleFromDb.isPresent()) {
			throw new RoleExistException();
		}
		Role newRole = new Role(null, roleName, null);
		Role createdRole = roleRepository.save(newRole);
		return commonMapper.convertToResponse(createdRole, RoleResponse.class);
	}

	@Override
	public RoleResponse updateRole(Long roleId, String roleName) {
		Role roleFromDb = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException());

		String rolename = roleFromDb.getName();
		if (rolename.equals("USER") || rolename.equals("ADMANAGER") || rolename.equals("ADMIN")) {
			throw new UpdateDefaultRoleException(roleFromDb.getName());
		}
		Optional<Role> existRoleFromDb = roleRepository.findByname(roleName);
		if (existRoleFromDb.isPresent()) {
			throw new RoleExistException(roleName);
		}
		roleFromDb.setName(roleName);
		Role updatedRole = roleRepository.save(roleFromDb);
		return commonMapper.convertToResponse(updatedRole, RoleResponse.class);
	}

	@Override
	@Transactional
	public String deleteRole(Long roleId) {
		Role roleFromDb = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFoundException());

		String rolename = roleFromDb.getName();
		if (rolename.equals("USER") || rolename.equals("ADMANAGER") || rolename.equals("ADMIN")) {
			throw new DeleteDefaultRoleException(roleFromDb.getName());
		}
		roleRepository.delete(roleFromDb);
		return "Role successfully deleted.";
	}
}
