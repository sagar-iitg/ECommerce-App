package com.sk.services;

import java.util.List;

import com.sk.dtos.PageableResponse;
import com.sk.dtos.ProductDto;

public interface ProductService {

	
	
	//create
	
	ProductDto create(ProductDto productDto);
	
	//update 
	
	
	ProductDto update(ProductDto productDto, String productId);
	//delete
	
	void delete(String ProductId);
	
	
	//get Single
	
	
	ProductDto get (String productId);
	
	//get all
	PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
	//getAll --LiveProducts
	PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
	//search product
	
	PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
	//create product with category
	ProductDto createWithCategory(ProductDto productDto,String categoryId);
	
	//update category of product
	
	ProductDto updateCategory(String productId, String categoryId);
		

	PageableResponse<ProductDto> getAllOfCategories(String categoryId,int pageNumber,int pageSize, String sortBy,String sortDir);
	
	//other methods
	
	
	
	
	
}
