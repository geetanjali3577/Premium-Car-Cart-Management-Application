package com.premiumcar.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AddToCartRequestDTO {

    @NotNull(message = "Premium Car ID is required")
    private Long premiumCarId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity = 1; // ✅ default quantity

}

