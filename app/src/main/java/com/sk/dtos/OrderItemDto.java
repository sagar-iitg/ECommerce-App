/**
 * @author
 * Sagar Kumar
 */
package com.sk.dtos;

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

public class OrderItemDto {

	private int orderItemId;
	private int quantity;
	private int totalPrice;	
	private ProductDto product;
		
}
