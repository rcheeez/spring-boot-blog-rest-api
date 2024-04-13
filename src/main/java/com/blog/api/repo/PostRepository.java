package com.blog.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	boolean existsByTitle(String title);
	
	List<Post> findByCategoryId(int categoryId);
}
