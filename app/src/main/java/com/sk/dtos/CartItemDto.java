package com.sk.dtos;

import com.sk.entities.Cart;
import com.sk.entities.CartItem;
import com.sk.entities.Product;
import com.sk.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;

}
