package com.springboot.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	List<Long> findAllIdsOfUserRoles(Long userId);
	
	@Query(value = "select u.id, u.address, u.address, u.first_name, u.is_blocked, "
			+ "u.last_name, u.phone_number, u.username, u.password from users u "
			+ "inner join users_roles ur on u.id = ur.user_entity_id "
			+ "inner join role r on r.id = ur.roles_id "
			+ "group by u.id "
			+ "having count(r.id) = 1" ,nativeQuery = true)
	Page<UserEntity> findAllUsers(Pageable pageable);
	
	@Query(value = "select count(all_user.id) from "
			+ "(select user_entity_id as id from users_roles "
			+ "group by user_entity_id "
			+ "having count(roles_id) = 1) all_user;" ,nativeQuery = true)
	Long countTotalUsers();
	
	@Query(value = "select u.id, u.address, u.address, u.first_name, u.is_blocked, u.last_name, "
			+ "u.phone_number, u.username, u.password from users u "
			+ "inner join users_roles ur on u.id = ur.user_entity_id "
			+ "inner join role r on r.id = ur.roles_id "
			+ "where r.name = 'ADMIN'" ,nativeQuery = true)
	Page<UserEntity> findAllAdmins(Pageable pageable);
	
	@Query(value = "select count(all_admin.id) "
			+ "from (select u.id as id from users u inner join users_roles ur on u.id = ur.user_entity_id "
			+ "inner join role r on r.id = ur.roles_id  "
			+ "where r.name = 'ADMIN') all_admin;" ,nativeQuery = true)
	Long countTotalAdmins();
}
