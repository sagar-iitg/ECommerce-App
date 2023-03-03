/**
 * @author
 * Sagar Kumar
 */
package com.sk.services;

import java.util.List;

import com.sk.dtos.CreateOrderRequest;
import com.sk.dtos.OrderDto;
import com.sk.dtos.PageableResponse;

public interface OrderService 
{

	//create order
	
	OrderDto createOrder(CreateOrderRequest orderDto);
	
	//remove order
	
	void removeOrder(String orderId);
	
	//get orders of user
	List<OrderDto> getOrdersOfUsers(String userId);
	
	
	
	//get orders
	
	
	PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//other methods related to order
	
	//update orders
	
	
	OrderDto updateOrder(OrderDto orderDto,String orderId);
	
	
	
}
