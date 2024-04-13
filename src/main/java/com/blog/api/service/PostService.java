package com.blog.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.dto.PostDto;
import com.blog.api.dto.PostResponse;
import com.blog.api.exception.NotFoundException;
import com.blog.api.model.Category;
import com.blog.api.model.Post;
import com.blog.api.repo.CategoryRepository;
import com.blog.api.repo.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	
	private CategoryRepository categoryRepository;
	
	private ModelMapper mapper;
	
	public PostService(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	public PostResponse listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		PostResponse response = new PostResponse();
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		List<Post> allPosts = posts.getContent();
		if (allPosts.isEmpty()) {
			throw new NotFoundException("There are No Posts!");
		}
		List<PostDto> content = allPosts.stream().map((post) -> mapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		response.setPosts(content);
		response.setPageNo(posts.getNumber());
		response.setPageSize(posts.getSize());
		response.setTotalElements(posts.getTotalElements());
		response.setTotalPages(posts.getTotalPages());
		response.setLast(posts.isLast());
		
		return response;
	}
	
	
	public PostDto getPostById(int id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post Not Found with this ID: "+id));
		return mapper.map(post, PostDto.class);
	}
	
	public List<PostDto> getPostByCategoryId(int categoryId) {
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		if (posts.isEmpty()) {
			throw new NotFoundException("No posts found under this category Id: "+categoryId);
		}
		
		return posts
				.stream().map((post) -> mapper.map(post, PostDto.class)).collect(Collectors.toList());
	}
	
	public PostDto createPost(PostDto postDto) {
		Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new NotFoundException("Category Not Found with this Id: "+postDto.getCategoryId()));
		
		Post post = mapper.map(postDto, Post.class);
		post.setCategory(category);
		postRepository.save(post);
		
		return mapper.map(post, PostDto.class);
	}
	
	public PostDto updatePostById(int id , PostDto postDto) {
		Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post Not Found with this Id: "+id));
		Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new NotFoundException("Category Not Found with this Id: "+ postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setCategory(category);
		post.setContent(postDto.getContent());
		
		Post updatedPost =  postRepository.save(post);
		
		return mapper.map(updatedPost, PostDto.class);
	}
	
	public void deletePostById(int id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post Not Found with this Id: "+id));
		postRepository.delete(post);
	}
	
	
}
