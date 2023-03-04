package com.sk.services.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sk.dtos.PageableResponse;
import com.sk.dtos.ProductDto;
import com.sk.entities.Category;
import com.sk.entities.Product;
import com.sk.exception.ResourceNotFoundException;
import com.sk.helper.Helper;
import com.sk.repositories.CategoryRepository;
import com.sk.repositories.ProductRepository;
import com.sk.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	ModelMapper mapper;
	
	//other dependency
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Override
	public ProductDto create(ProductDto productDto) {
		// TODO Auto-generated method stub
		
		
		Product product=mapper.map(productDto, Product.class);
		
		String productId=UUID.randomUUID().toString();
		product.setProductId(productId);
		
		//date
		product.setAddedDate(new Date());
		Product saveProduct=productRepository.save(product);
		return mapper.map(saveProduct, ProductDto.class);
		
		
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		// TODO Auto-generated method stub
		
		
		//fetch the product of given id
		
		Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with given id is not available"));
		
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		product.setProductImage(productDto.getProductImage());
		
		//save the entity
		Product updatedproduct=productRepository.save(product);
		
		
		return mapper.map(updatedproduct, ProductDto.class);
		
	}

	@Override
	public void delete(String productId) {
		// TODO Auto-generated method stub
		Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with given id is not available"));
		productRepository.delete(product);
		
		
	
	}

	@Override
	public ProductDto get(String productId) {
		// TODO Auto-generated method stub
		
		
		Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with given id is not available"));
		return mapper.map(product, ProductDto.class);
		
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		
		
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Product> page=productRepository.findAll(pageable);
		
		return Helper.getPageableResponse(page, ProductDto.class);
		
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub

		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Product> page=productRepository.findByLiveTrue(pageable);
		
		
		return Helper.getPageableResponse(page, ProductDto.class);
		
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		
		
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Product> page=productRepository.findByTitleContaining(subTitle,pageable);
		
		
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
		
		//fetchThecategory from db
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category not found !!"));
	
		
		Product product=mapper.map(productDto, Product.class);
		
		String productId=UUID.randomUUID().toString();
		product.setProductId(productId);
		
		//date
		product.setAddedDate(new Date());
		product.setCategory(category);
		Product saveProduct=productRepository.save(product);
		return mapper.map(saveProduct, ProductDto.class);
		
		
		
	}

	@Override
	public ProductDto updateCategory(String productId, String categoryId) {

		//product fetch
		 Product product=productRepository.findById(productId).
		 orElseThrow(()-> new ResourceNotFoundException("Product of given id is not found"));
		 
		 
		 Category category=categoryRepository.findById(categoryId).
				 orElseThrow(()-> new ResourceNotFoundException("Category of given id not found"));
		 
		 product.setCategory(category);
		 Product savedProduct=productRepository.save(product);
		 
		return mapper.map(savedProduct, ProductDto.class);
		
	}

	@Override
	public PageableResponse<ProductDto> getAllOfCategories(String categoryId,int pageNumber,int pageSize, String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		
		 Category category=categoryRepository.findById(categoryId).
				 orElseThrow(()-> new ResourceNotFoundException("Category of given id not found"));
		 
			Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		 Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page=productRepository.findByCategory(category,pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
		
	}
	

}
