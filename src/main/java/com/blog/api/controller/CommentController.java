package com.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.dto.CommentDto;
import com.blog.api.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService service;
	
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> showAllComments(@PathVariable int postId) {
		List<CommentDto> comments = service.listAllComments(postId);
		return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> showCommentById(@PathVariable int postId, @PathVariable int commentId) {
		CommentDto comment = service.findCommentById(postId, commentId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
	}
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto, @PathVariable int postId) {
		CommentDto comment = service.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);
	}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable int postId, @PathVariable int commentId, 
													@RequestBody CommentDto commentDto) {
		CommentDto comment = service.updateCommentById(postId, commentId, commentDto);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable int postId, @PathVariable int commentId) {
		service.deleteCommentById(postId, commentId);
		return new ResponseEntity<String>("Comment Deleted Successfully!", HttpStatus.OK);
	}
}
