package com.sk.services.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.dtos.AddItemToCartRequest;
import com.sk.dtos.CartDto;
import com.sk.entities.Cart;
import com.sk.entities.CartItem;
import com.sk.entities.Product;
import com.sk.entities.User;
import com.sk.exception.BadApiRequestException;
import com.sk.exception.ResourceNotFoundException;
import com.sk.repositories.CartItemRepository;
import com.sk.repositories.CartRepository;
import com.sk.repositories.ProductRepository;
import com.sk.repositories.UserRepository;
import com.sk.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	private CartItemRepository cartItemRepository;
	
	
	
	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		// TODO Auto-generated method stub

		int quantity = request.getQuantity();
		String productId = request.getProductId();

		
		
		if(quantity<=0)
		{
			throw new BadApiRequestException("Requested Quantity is not valid!! ");
		}
		// fetch the product

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product not found in db"));
		// fetch the user from db;

		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Cart cart = null;

		try {

			cart = cartRepository.findByUser(user).get();

		} catch (NoSuchElementException ex) {

			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());

		}

		// perform cart Operation

		AtomicReference<Boolean> updated=new AtomicReference<>(false);
		
		List<CartItem> items = cart.getItems();

		items= items.stream().map(item -> {

			if (item.getProdcut().getProductId().equals(productId)) {

				// item already present in cart
				
				item.setQuantity(quantity);
				item.setTotalPrice(quantity*product.getDiscountedPrice());
				updated.set(true);
				
				

			}
			return item; 

		}).collect(Collectors.toList());

		// create items
		//cart.setItems(updatedItems);
		
		if(!updated.get()) {
			CartItem cartItem = CartItem.builder().
					quantity(quantity).
					totalPrice(quantity * product.getDiscountedPrice()).cart(cart)
					.prodcut(product).build();

			cart.getItems().add(cartItem);
		}
		

		cart.setUser(user);

		Cart updatedCart = cartRepository.save(cart);

		return mapper.map(updatedCart, CartDto.class);

	}

	@Override
	public void removeFromCart(String userId, int cartItem) {
		
		
			
		
		CartItem cartItem2 = cartItemRepository.findById(cartItem).orElseThrow(()->
		new ResourceNotFoundException("Cart item not found in db"));
		
		cartItemRepository.delete(cartItem2);
		
	}

	@Override
	public void clearCart(String userId) {
		// TODO Auto-generated method stub
		

		User user = userRepository.
				findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		
		Cart cart=cartRepository.findByUser(user).
		orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));
		
		cart.getItems().clear();
		
		cartRepository.save(cart);
		

		

	}

	@Override
	public CartDto getCartByUser(String userId) {
		// TODO Auto-generated method stub
		
		
		User user = userRepository.
				findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		
		Cart cart=cartRepository.findByUser(user).
		orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found"));
			
		return mapper.map(cart, CartDto.class);
		
	}

}
