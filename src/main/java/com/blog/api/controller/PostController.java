package com.blog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.dto.PostDto;
import com.blog.api.dto.PostResponse;
import com.blog.api.service.PostService;
import com.blog.api.utilities.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService service;
	
	@GetMapping
	public ResponseEntity<PostResponse> showAllPosts(@RequestParam(required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
													 @RequestParam(required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
													 @RequestParam(required = false, defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
													 @RequestParam(required = false, defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir) {
		PostResponse response = service.listAllPosts(pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> showPostById(@PathVariable int id) {
		PostDto postDto = service.getPostById(id);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> showPostsByCategoryId(@PathVariable int categoryId) {
		List<PostDto> posts = service.getPostByCategoryId(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto) {
		PostDto post = service.createPost(postDto);
		return new ResponseEntity<PostDto>(post, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int id) {
		PostDto post = service.updatePostById(id, postDto);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePost(@PathVariable int id) {
		service.deletePostById(id);
		return new ResponseEntity<String>("Post Deleted Successfully!", HttpStatus.OK);
	}
	
}
