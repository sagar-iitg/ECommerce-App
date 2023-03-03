package com.sk.dtos;

import java.util.Date;

import com.sk.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {

	private String productId; 
	private String title;		
	private String description;
	private int price;
	private int discountedPrice;
	private int quantity;
	private Date addedDate;	
	private boolean live;
	private boolean stock;
	private String productImage;
	private CategoryDto category;
	
	
	
	
}
