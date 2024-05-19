package com.sagar.electronic.store.repositories;

import com.sagar.electronic.store.entities.Cart;
import com.sagar.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {


    Optional<Cart> findByUser(User user);

}
