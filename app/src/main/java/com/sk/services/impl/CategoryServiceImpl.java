package com.sk.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sk.dtos.CategoryDto;
import com.sk.dtos.PageableResponse;
import com.sk.entities.Category;
import com.sk.exception.ResourceNotFoundException;
import com.sk.helper.Helper;
import com.sk.repositories.CategoryRepository;
import com.sk.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	// create
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		
		
		
		// creating id for category
		
		String categoryId=UUID.randomUUID().toString();	
		categoryDto.setCategoryId(categoryId);
		 
		
		Category category = mapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);

		return mapper.map(savedCategory, CategoryDto.class);

	}

	// update

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {

		// get category
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with given category Id is not found!!"));

		// update category details
		category.setTitle(categoryDto.getTitle());
		category.setCoverImage(categoryDto.getCoverImage());
		category.setDescription(categoryDto.getDescription());
		Category updateCategory = categoryRepository.save(category);

		return mapper.map(updateCategory, CategoryDto.class);

	}

	// delete
	@Override
	public void delete(String categoryId) 
	{
		// get category
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with given category Id is not found!!"));
		
		categoryRepository.delete(category);
		
		
		
		
	}

	// get-all
	
	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir)
	{
		
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
 
		
		
		Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
		
		Page<Category> page=categoryRepository.findAll(pageable);
		
		PageableResponse<CategoryDto> pageableResponse=Helper.getPageableResponse(page, CategoryDto.class);
		
		 
		
		
		
		
		return pageableResponse;
	}

	// single category detail

	@Override
	public CategoryDto get(String categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with given category Id is not found!!"));
		
		
		
		return mapper.map(category, CategoryDto.class);
		
	}

	// search

}
