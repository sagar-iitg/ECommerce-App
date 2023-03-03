package com.sk.entities;

/**
 * @author 
 * Sagar Kumar
 *
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name="orders")
public class Order {

	@Id
	private String orderId;
	
	// PENDING, DELIVERED, DISPATCHED
	
	//enum 
	private String orderStatus;
	
	//NOT-PAID, PAID
	//enum
	//boolean-false => NOT-PAID || true=>PAID
	
	
	private String paymentStatus;
	private int orderAmount;
	@Column(length=5000)
	private String billingAddress;
	private String billingPhone;
	private String billingName;
	private Date orderedDate;
	private Date deliveredDate;
	//user
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	
	@OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<OrderItem> orderItems=new ArrayList<>();
	
	
}
