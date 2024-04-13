package com.blog.api.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.model.Role;

public interface RoleRepository  extends JpaRepository<Role, Integer>{

	Optional<Role> findByName(String name);
}
