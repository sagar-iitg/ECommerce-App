package com.sk.dtos;

import com.sk.entities.Order;
import com.sk.entities.Product;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {


    private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;


}
