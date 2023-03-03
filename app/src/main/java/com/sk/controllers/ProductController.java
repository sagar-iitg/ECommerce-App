package com.sk.controllers;

/**
 * @author Sagar Kumar
 * 
 */

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sk.dtos.ApiResponseMessage;
import com.sk.dtos.ImageResponse;
import com.sk.dtos.PageableResponse;
import com.sk.dtos.ProductDto;
import com.sk.services.FileService;
import com.sk.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/products")
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${product.image.path}")
	private String imagePath;

	
	//create
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
	{
		
		ProductDto createdproductDto=productService.create(productDto);
		return new ResponseEntity<>(createdproductDto,HttpStatus.CREATED);
		
		
	}
	
	
	//update
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable("productId") String productId)
	{
		ProductDto updatedProductDto=productService.update(productDto, productId);
		return new ResponseEntity<>(updatedProductDto,HttpStatus.OK);
	}
	
	//delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable("productId") String productId)
	{
		

	
		
		
		productService.delete(productId);
		ApiResponseMessage msg = ApiResponseMessage.builder().message("Product is deleted successfully").success(true)
				.status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}
	
	//get single
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") String productId)
	{
		

	
		return new ResponseEntity<>(productService.get(productId),HttpStatus.OK);
	}
	
	
	//get all
	
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
	{
		

		
		return new ResponseEntity<>(productService.getAll(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	}
	
	
	//getAllLive
	
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLive(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
	{
		

		
		return new ResponseEntity<>(productService.getAllLive(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	}
	
	//search
	
	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
			@PathVariable String query,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
	{
		

		return new ResponseEntity<>(productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	}
	
	// upload image 
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(
			@PathVariable String productId, @RequestParam("productImage")MultipartFile image) throws IOException{
		
		
		String fileName=fileService.uploadFile(image,imagePath);
		
		ProductDto productDto=productService.get(productId);
		
		productDto.setProductImage(fileName);
		ProductDto updatedProduct=productService.update(productDto, productId);
		ImageResponse response=ImageResponse.builder().imageName(updatedProduct.getProductImage()).
		message("Product Image is successfully uploaded").status(HttpStatus.CREATED).success(true).build();
		
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
		
	}
	
	
	//serve image
	

	
	@GetMapping("/image/{productId}")
	public void serveUserImage(@PathVariable String productId,HttpServletResponse response) throws IOException
	{
		
		// 
		
		ProductDto productDto=productService.get(productId);
		//logger.info("user image name {} ",user.getImageName());
		InputStream resource=fileService.getResource(imagePath, productDto.getProductImage());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource,response.getOutputStream());
		
		
		
		
	}
	
	
}
