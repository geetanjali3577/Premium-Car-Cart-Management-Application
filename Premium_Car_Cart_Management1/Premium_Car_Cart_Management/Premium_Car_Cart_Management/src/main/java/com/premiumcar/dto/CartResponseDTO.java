package com.premiumcar.dto;

import com.premiumcar.entity.CartStatus;
import lombok.Data;

import java.util.List;
@Data
public class CartResponseDTO {
    private Long cartId;
    private Long userId;
    private CartStatus status;
    private List<CartItemResponseDTO> items;
}

