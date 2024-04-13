package com.blog.api.dto;

import java.util.ArrayList;
import java.util.List;

import com.blog.api.model.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private int id;
	private String title;
	private String description;
	private String content;
	private List<Comment> comments = new ArrayList<Comment>();
	private int categoryId;
}
