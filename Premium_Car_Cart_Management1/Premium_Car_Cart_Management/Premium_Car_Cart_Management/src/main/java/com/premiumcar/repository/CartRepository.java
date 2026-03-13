package com.premiumcar.repository;

import com.premiumcar.entity.Cart;
import com.premiumcar.entity.CartStatus;
import com.premiumcar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find cart by User entity
    Optional<Cart> findByUser(User user);

    // Find cart by User ID
    Optional<Cart> findByUserId(Long userId);

    Optional<Cart> findByUserIdAndStatus(Long userId, CartStatus cartStatus);
}
