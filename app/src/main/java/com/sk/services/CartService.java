package com.sk.services;

import com.sk.dtos.AddItemToCartRequest;
import com.sk.dtos.CartDto;

public interface CartService {

	
	//add items to cart:
	//case1: cart for user is not available : we will create cart
	//case2: cart available add items to the cart
	
	CartDto addItemToCart(String userId, AddItemToCartRequest request);
	
	
	//remove item from cart
	
	
	void removeFromCart(String userId, int cartItem);
	
	
	//clear cart
	void clearCart(String userId);
	
	
	
	CartDto getCartByUser(String userId);
	
	
	
	
}
