/**
 * @author
 * Sagar Kumar
 */
package com.sk.services.impl;


import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sk.dtos.CreateOrderRequest;
import com.sk.dtos.OrderDto;
import com.sk.dtos.PageableResponse;
import com.sk.entities.Cart;
import com.sk.entities.CartItem;
import com.sk.entities.Order;
import com.sk.entities.OrderItem;
import com.sk.entities.User;
import com.sk.exception.BadApiRequestException;
import com.sk.exception.ResourceNotFoundException;
import com.sk.helper.Helper;
import com.sk.repositories.CartRepository;
import com.sk.repositories.OrderRepository;
import com.sk.repositories.UserRepository;
import com.sk.services.OrderService;


@Service
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	
	private ModelMapper mapper;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto) {
		 
		//fetch	
		User user= userRepository.findById(orderDto.getUserId()).
		orElseThrow(()->new ResourceNotFoundException("user not found"));
				
		//fetch cart
		
		Cart cart = cartRepository.findById(orderDto.getCartId()).
		orElseThrow(()->new ResourceNotFoundException("cart with given id  not found"));
				
		List<CartItem> cartItems = cart.getItems();
		
		if(cartItems.size()<=0)
		{
			throw new BadApiRequestException("Invalid no of items in cart");
		}
		
		//other checks
		
		Order order=Order.builder()
		.billingName(orderDto.getBillingName())
		.billingPhone(orderDto.getBillingPhone())
		.billingAddress(orderDto.getBillingAddress())
		.orderedDate(new Date())
		.deliveredDate(null)
		.paymentStatus(orderDto.getPaymentStatus())
		.orderStatus(orderDto.getOrderStatus())
		.orderId(UUID.randomUUID().toString())
		.user(user)
		.build();
		
		//orderItems and Amount is not set
		AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
		
		
		
		
		List<OrderItem> orderItems=cartItems.stream().map(cartItem->{
			//CartItem -->  OrderItem
			
			OrderItem orderItem=OrderItem.builder()
			.quantity(cartItem.getQuantity())
			.product(cartItem.getProdcut())
			.totalPrice(cartItem.getQuantity()*cartItem.getProdcut().getDiscountedPrice())
			.order(order).build();
			
			orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
			return orderItem;
		}).collect(Collectors.toList());
		
		
		
		order.setOrderItems(orderItems);
		order.setOrderAmount(orderAmount.get());
		
		//
		
		cart.getItems().clear();
		cartRepository.save(cart);
		Order savedOrder = orderRepository.save(order);
		
		
		return mapper.map(savedOrder,OrderDto.class);
		
	}

	@Override
	public void removeOrder(String orderId) 
	{
		
		Order order = orderRepository.findById(orderId).
		orElseThrow(()->new ResourceNotFoundException("order is not found"));
		
		
		orderRepository.delete(order);
		
	}

	@Override
	public List<OrderDto> getOrdersOfUsers(String userId) {
		// TODO Auto-generated method stub
		
		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));
		List<Order> orders = orderRepository.findByUser(user);
		
		
		List<OrderDto>  orderDtos= orders.stream().map(order->mapper.map(order,OrderDto.class)).collect(Collectors.toList());
		return orderDtos;
		
		
		
	}

	@Override
	public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// TODO Auto-generated method stub
		
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Order> page=orderRepository.findAll(pageable);
		return Helper.getPageableResponse(page, OrderDto.class);
		
		
		
		
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, String orderId) {
		// TODO Auto-generated method stub
		
		
		Order order=orderRepository.findById(orderId).
		orElseThrow(()-> new ResourceNotFoundException("order not found"));
		
		
		order.setDeliveredDate(new Date());
		
		order.setPaymentStatus(orderDto.getPaymentStatus());
		order.setOrderStatus(orderDto.getOrderStatus());
		Order updatedOrder=orderRepository.save(order);
		
		return mapper.map(updatedOrder, OrderDto.class);
		
	}

	
	
	
}
