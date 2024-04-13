package com.blog.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsernameOrEmail(String username, String email);
	
	boolean existsByUsername(String username);
	
	boolean existsByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
}
