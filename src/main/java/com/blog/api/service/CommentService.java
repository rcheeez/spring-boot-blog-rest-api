package com.blog.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.blog.api.dto.CommentDto;
import com.blog.api.exception.BadRequestException;
import com.blog.api.exception.NotFoundException;
import com.blog.api.model.Comment;
import com.blog.api.model.Post;
import com.blog.api.repo.CommentRepository;
import com.blog.api.repo.PostRepository;

@Service
public class CommentService {

	private PostRepository postRepository;
	private CommentRepository commentRepository;
	private ModelMapper mapper;
	
	public CommentService(PostRepository postRepository, CommentRepository commentRepository, ModelMapper mapper) {
		super();
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.mapper = mapper;
	}
	
	public List<CommentDto> listAllComments(int postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		if (comments.isEmpty()) {
			throw new NotFoundException("There are no comments in this post!");
		}
		return comments.stream().map((comment) -> mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}
	
	public CommentDto findCommentById(int postId, int commentId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found with this Id: "+postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment Not Found with this Id: "+commentId));
		if (comment.getPost().getId() != post.getId()) {
			throw new BadRequestException("Comment Doesnt Belongs to this post!");
		}
		return mapper.map(comment, CommentDto.class);
	}
	
	public CommentDto createComment(CommentDto commentDto, int postId) {
		Comment comment = mapper.map(commentDto, Comment.class);
		Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found with this Id: "+postId));
		
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);
		
		return mapper.map(savedComment, CommentDto.class);
	}
	
	public CommentDto updateCommentById(int postId, int commentId, CommentDto commentDto) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found with this Id: "+postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment Not Found with this Id: "+commentId));
		if (comment.getPost().getId() != post.getId()) {
			throw new BadRequestException("Comment Doesnt Belongs to this post!");
		}
		
		comment.setBody(comment.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		return mapper.map(updatedComment, CommentDto.class);
	}
	
	public void deleteCommentById(int postId, int commentId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found with this Id: "+postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment Not Found with this Id: "+commentId));
		if (comment.getPost().getId() != post.getId()) {
			throw new BadRequestException("Comment Doesnt Belongs to this post!");
		}
		commentRepository.delete(comment);
	}
}
