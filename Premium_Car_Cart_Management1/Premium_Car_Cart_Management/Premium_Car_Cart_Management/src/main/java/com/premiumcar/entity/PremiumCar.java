package com.premiumcar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "premium_car")
@Data
public class PremiumCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Brand is mandatory")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "Model is mandatory")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "Registration number is mandatory")
    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @NotNull
    private int price;

    @NotNull
    private Boolean approved;

    @NotNull
    private Boolean booked;


}
