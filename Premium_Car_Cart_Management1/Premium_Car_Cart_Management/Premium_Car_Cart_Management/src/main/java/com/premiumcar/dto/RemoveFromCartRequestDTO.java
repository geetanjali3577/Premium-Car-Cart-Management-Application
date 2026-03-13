package com.premiumcar.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RemoveFromCartRequestDTO {
    @NotNull(message = "Premium Car ID is required")
    private Long premiumCarId;

    public Long getPremiumCarId() {
        return premiumCarId;
    }

    public void setPremiumCarId(Long premiumCarId) {
        this.premiumCarId = premiumCarId;
    }
}
