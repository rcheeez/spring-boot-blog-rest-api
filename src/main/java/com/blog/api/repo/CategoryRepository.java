package com.blog.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
}
