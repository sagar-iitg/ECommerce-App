package com.sk.repositories;

import com.sk.entities.Cart;
import com.sk.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {


    Optional<Cart> findByUser(User user);

}
