/**
 * @author
 * Sagar Kumar
 */
package com.sk.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OrderDto {

	private String orderId;
	private String orderStatus="PENDING";
	private String paymentStatus="NOTPAID";
	private int orderAmount;
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date orderedDate=new Date();
	private Date deliveredDate;
	//user
	//private User user;
	
	
	private List<OrderItemDto> orderItems=new ArrayList<>();
	
	
}
