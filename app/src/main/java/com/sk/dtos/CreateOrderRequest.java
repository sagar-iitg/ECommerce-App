/**
 * @author
 * Sagar Kumar
 */
package com.sk.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CreateOrderRequest 
{
	

	@NotBlank(message="cart is required!! ")
	private String cartId;
	@NotBlank(message="userId is required")
	private String userId;
	
	private String orderStatus="PENDING";
	private String paymentStatus="NOTPAID";
	
	@NotBlank(message="Addres  is required")
	private String billingAddress;
	@NotBlank(message="phone no is required")
	private String billingPhone;
	@NotBlank(message="billing name is required")
	private String billingName;
	

}
