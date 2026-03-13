package com.premiumcar.mapper;

import com.premiumcar.dto.CartItemResponseDTO;
import com.premiumcar.dto.CartResponseDTO;
import com.premiumcar.entity.Cart;
import com.premiumcar.entity.CartItem;
import com.premiumcar.entity.PremiumCar;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponseDTO toResponse(Cart cart) {

        if (cart == null) {
            return null;
        }

        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUser().getId());
        response.setStatus(cart.getStatus());

        // Map cart items safely (Java 7 compatible)
        List<CartItemResponseDTO> itemDtos = new ArrayList<CartItemResponseDTO>();

        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                // 🔥 NPE PROTECTION
                if (item != null && item.getPremiumCar() != null) {
                    CartItemResponseDTO dto = mapCartItem(item);
                    itemDtos.add(dto);
                }
            }
        }

        response.setItems(itemDtos);
        return response;
    }

    private CartItemResponseDTO mapCartItem(CartItem item) {

        PremiumCar car = item.getPremiumCar();

        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setPremiumCarId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setQuantity(item.getQuantity());

        return dto;
    }
}