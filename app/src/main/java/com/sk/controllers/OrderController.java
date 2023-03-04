/**
 * @author
 * Sagar Kumar
 */
package com.sk.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sk.dtos.ApiResponseMessage;
import com.sk.dtos.CreateOrderRequest;
import com.sk.dtos.OrderDto;
import com.sk.dtos.PageableResponse;
import com.sk.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController
{
	
	@Autowired
	private OrderService orderService;
	
	
	
	@PostMapping
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request)
	{
		OrderDto createOrder = orderService.createOrder(request);
		
		return new ResponseEntity<OrderDto>(createOrder,HttpStatus.CREATED);
		
		
	}
	
	
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId)
	{
		orderService.removeOrder(orderId);
		
		ApiResponseMessage responseMessage = ApiResponseMessage.builder().status(HttpStatus.OK).message("order is removed").success(true).build();
		
		return new ResponseEntity<>(responseMessage,HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId)
	{
		
		List<OrderDto> ordersOfUsers = orderService.getOrdersOfUsers(userId);
		
		
		return new ResponseEntity<List<OrderDto>>(ordersOfUsers,HttpStatus.OK);
		
	}
	
	
	@GetMapping	
	public ResponseEntity<PageableResponse<OrderDto>> getOrders(
		
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
	
	{
		
                    PageableResponse<OrderDto> orders=orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);		
		
		return new ResponseEntity<>(orders,HttpStatus.OK);
		
	}
	//update order
	
	
	@PutMapping("/update/{orderId}")
	public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto,@PathVariable String orderId)
	{
		
		
		OrderDto orderDto2=orderService.updateOrder(orderDto, orderId);
		
		return new ResponseEntity<OrderDto>(orderDto2,HttpStatus.OK);
		
	}
	
	
	

	
	
	
}
