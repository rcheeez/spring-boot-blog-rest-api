package com.blog.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.dto.CategoryDto;
import com.blog.api.exception.NotFoundException;
import com.blog.api.model.Category;
import com.blog.api.repo.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	public List<CategoryDto> listAllCategories() {
		List<Category> categories = repository.findAll();
		if (categories.isEmpty()) {
			throw new NotFoundException("There are no categories!!");
		}
		return categories.stream().map((category) -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
	}
	
	public CategoryDto getCategoryById(int id) {
		Category category = repository.findById(id).orElseThrow(() -> new NotFoundException("Category Not Found with this Id: "+id));
		return mapper.map(category, CategoryDto.class);
	}
	
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		Category savedCategory = repository.save(category);
		return mapper.map(savedCategory, CategoryDto.class);
	}
	
	public void deleteCategoryById(int id) {
		Category category = repository.findById(id).orElseThrow(() -> new NotFoundException("Category Not Found with this Id: "+id));
		repository.delete(category);
	}
}
