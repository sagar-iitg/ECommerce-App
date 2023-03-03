package com.sk.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class AddItemToCartRequest {
	
	
	private String productId;
	private int quantity;
	

	
	
}
