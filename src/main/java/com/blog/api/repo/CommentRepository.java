package com.blog.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	List<Comment> findByPostId(int postId);
}
