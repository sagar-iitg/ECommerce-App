package com.sk.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.entities.Cart;
import com.sk.entities.User;


public interface CartRepository extends JpaRepository<Cart,String> {
	
	Optional<Cart> findByUser(User user);
	

}
