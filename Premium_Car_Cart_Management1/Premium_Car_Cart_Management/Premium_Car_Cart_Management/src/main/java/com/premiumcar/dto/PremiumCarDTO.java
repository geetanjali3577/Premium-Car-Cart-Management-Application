package com.premiumcar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PremiumCarDTO {

    private Long id;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotBlank(message = "Registration number is mandatory")
    private String registrationNumber;

    @NotNull(message = "Approval status is required")
    private Boolean approved;

    @NotNull(message = "Booking status is required")
    private Boolean booked;
}

