package com.premiumcar.mapper;

import com.premiumcar.dto.PremiumCarDTO;
import com.premiumcar.entity.PremiumCar;

public class PremiumCarMapper {

    private PremiumCarMapper() {
    }

    public static PremiumCarDTO toDto(PremiumCar car) {
        PremiumCarDTO dto = new PremiumCarDTO();
        dto.setId(car.getId());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setRegistrationNumber(car.getRegistrationNumber());
        dto.setApproved(car.getApproved());
        dto.setBooked(car.getBooked());
        return dto;
    }

    public static PremiumCar toEntity(PremiumCarDTO dto) {
        PremiumCar car = new PremiumCar();
        car.setId(dto.getId());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setRegistrationNumber(dto.getRegistrationNumber());
        car.setApproved(dto.getApproved());
        car.setBooked(dto.getBooked());
        return car;
    }
}
