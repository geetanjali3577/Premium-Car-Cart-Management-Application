package com.premiumcar.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long premiumCarId;
    private String brand;
    private String model;
    private Integer quantity;
}
