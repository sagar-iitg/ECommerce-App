/**
 * @author
 * Sagar Kumar
 */
package com.sk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sk.entities.Order;
import com.sk.entities.User;

public interface OrderRepository extends JpaRepository<Order, String>{
	
	List<Order> findByUser(User user);
	
	
}
