package com.premiumcar.service;

import com.premiumcar.dto.PremiumCarDTO;

import java.util.List;

public interface PremiumCarService {
    PremiumCarDTO createPremiumCar(PremiumCarDTO dto);

    PremiumCarDTO getPremiumCarById(Long id);

    List<PremiumCarDTO> getAllPremiumCars();

    PremiumCarDTO updatePremiumCar(Long id, PremiumCarDTO dto);

    boolean deletePremiumCar(Long id);
}
