package com.sk.dtos;

import java.util.Date;
import java.util.List;

import com.sk.entities.Cart;
import com.sk.entities.CartItem;
import com.sk.entities.User;

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
