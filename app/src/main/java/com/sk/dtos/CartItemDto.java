package com.sk.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

	
	
	private int cartItemId;
	private ProductDto prodcut;
	private int quantity;
	private int totalPrice;
	
	
	
}
