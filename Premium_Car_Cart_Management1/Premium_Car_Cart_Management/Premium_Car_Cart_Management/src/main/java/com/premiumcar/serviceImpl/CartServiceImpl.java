package com.premiumcar.serviceImpl;

import com.premiumcar.dto.AddToCartRequestDTO;
import com.premiumcar.dto.CartResponseDTO;
import com.premiumcar.dto.RemoveFromCartRequestDTO;
import com.premiumcar.entity.*;
import com.premiumcar.exception.ApiException;
import com.premiumcar.mapper.CartMapper;
import com.premiumcar.repository.CartItemRepository;
import com.premiumcar.repository.CartRepository;
import com.premiumcar.repository.PremiumCarRepository;
import com.premiumcar.repository.UserRepository;
import com.premiumcar.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.premiumcar.dto.CartItemResponseDTO;
import com.premiumcar.entity.Cart;
import com.premiumcar.entity.CartItem;
import com.premiumcar.entity.PremiumCar;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final PremiumCarRepository premiumCarRepository;
    private final CartMapper cartMapper;
    private final UserRepository userRepository;
    // ================= ADD TO CART =================
    @Override
    public CartResponseDTO addToCart(Long userId, AddToCartRequestDTO request) {

        // 1️⃣ Validate premium car
        PremiumCar car = premiumCarRepository.findById(request.getPremiumCarId())
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "Premium car not found"));

        // 2️⃣ Get or create ACTIVE cart
        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseGet(() -> createCart(userId));

        // 3️⃣ Prevent duplicate & handle quantity
        CartItem item = cartItemRepository
                .findByCartIdAndPremiumCarId(cart.getId(), car.getId())
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setPremiumCar(car);
            newItem.setQuantity(request.getQuantity());

            cart.getCartItems().add(newItem);
        }

        return cartMapper.toResponse(cart);
    }

    // ================= REMOVE FROM CART =================
    @Override
    public CartResponseDTO removeFromCart(Long userId, RemoveFromCartRequestDTO request) {

        // 1️⃣ Validate cart ownership
        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "Active cart not found"));

        // 2️⃣ Validate premium car
        PremiumCar car = premiumCarRepository
                .findById(request.getPremiumCarId())
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "Premium car not found"));

        // 3️⃣ Validate cart item
        CartItem item = cartItemRepository
                .findByCartIdAndPremiumCarId(cart.getId(), car.getId())
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "Premium car not found in cart"));

        // 4️⃣ Remove item
        cart.getCartItems().remove(item);

        return cartMapper.toResponse(cart);
    }

    // ================= GET CART =================
    @Override
    public CartResponseDTO getCartByUser(Long userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "Cart not found for user"));

        return cartMapper.toResponse(cart);
    }

    // ================= HELPER =================
    private Cart createCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setStatus(CartStatus.ACTIVE);
        cart.setCreatedAt(LocalDateTime.now());

        return cartRepository.save(cart);
    }
}
