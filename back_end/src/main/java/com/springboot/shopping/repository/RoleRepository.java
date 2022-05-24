package com.springboot.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.shopping.model.Role;
import com.springboot.shopping.model.UserEntity;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(value = "select * from role where name != 'ADMANAGER'" ,nativeQuery = true)
	List<Role> findAll();
	
	Optional<Role> findByname(String rolename);
}
