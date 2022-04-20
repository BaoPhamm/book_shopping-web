package com.springboot.shopping.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByname(String rolename);
}
