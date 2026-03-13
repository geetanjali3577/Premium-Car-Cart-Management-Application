package com.premiumcar.controller;

import com.premiumcar.dto.AddToCartRequestDTO;
import com.premiumcar.dto.CartResponseDTO;
import com.premiumcar.dto.RemoveFromCartRequestDTO;
import com.premiumcar.exception.ApiResponse;
import com.premiumcar.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //  ADD TO CART
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody AddToCartRequestDTO request) {

        CartResponseDTO cartResponse = cartService.addToCart(userId, request);

        ApiResponse<CartResponseDTO> response =
                new ApiResponse<>("Item added to cart successfully", cartResponse);

        return ResponseEntity.ok(response);
    }

    //  REMOVE FROM CART
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> removeFromCart(
            @PathVariable Long userId,
            @Valid @RequestBody RemoveFromCartRequestDTO request) {

        CartResponseDTO cartResponse = cartService.removeFromCart(userId, request);

        ApiResponse<CartResponseDTO> response =
                new ApiResponse<>("Item removed from cart successfully", cartResponse);

        return ResponseEntity.ok(response);
    }
    //  GET CART BY USER
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCartByUser(
            @PathVariable Long userId) {

        CartResponseDTO cartResponse = cartService.getCartByUser(userId);

        ApiResponse<CartResponseDTO> response =
                new ApiResponse<>("Cart data fetched successfully", cartResponse);

        return ResponseEntity.ok(response);
    }
}