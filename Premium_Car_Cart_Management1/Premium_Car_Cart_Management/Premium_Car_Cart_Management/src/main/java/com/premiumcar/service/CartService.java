package com.premiumcar.service;

import com.premiumcar.dto.AddToCartRequestDTO;
import com.premiumcar.dto.CartResponseDTO;
import com.premiumcar.dto.RemoveFromCartRequestDTO;

public interface CartService  {
    CartResponseDTO addToCart(Long userId, AddToCartRequestDTO request);
    CartResponseDTO removeFromCart(Long userId, RemoveFromCartRequestDTO request);
    CartResponseDTO getCartByUser(Long userId);

}
