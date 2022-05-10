package com.springboot.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.shopping.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);
	
	Optional<UserEntity> findByPhoneNumber(String phoneNumber);

	@Query(value = "select ur.roles_id from users u "
			+ "inner join users_roles ur on u.id = ur.user_entity_id "
			+ "where ur.user_entity_id = :userId" ,nativeQuery = true)
	List<Long> findAllIdsOfRoles(Long userId);
}
