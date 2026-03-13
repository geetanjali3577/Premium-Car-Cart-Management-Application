package com.premiumcar.serviceImpl;

import com.premiumcar.dto.PremiumCarDTO;
import com.premiumcar.entity.PremiumCar;
import com.premiumcar.exception.ApiException;
import com.premiumcar.mapper.PremiumCarMapper;
import com.premiumcar.repository.PremiumCarRepository;
import com.premiumcar.service.PremiumCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PremiumCarServiceImpl implements PremiumCarService {

    private final PremiumCarRepository premiumCarRepository;

    @Override
    public PremiumCarDTO createPremiumCar(PremiumCarDTO dto) {

        // Duplicate Registration Number
        if (premiumCarRepository.existsByRegistrationNumber(dto.getRegistrationNumber())) {
            throw new ApiException(
                    HttpStatus.CONFLICT,
                    "Registration number already exists"
            );
        }

        PremiumCar car = PremiumCarMapper.toEntity(dto);
        PremiumCar savedCar = premiumCarRepository.save(car);
        return PremiumCarMapper.toDto(savedCar);
    }

    @Override
    public PremiumCarDTO getPremiumCarById(Long id) {

        PremiumCar car = premiumCarRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                HttpStatus.NOT_FOUND,
                                "Premium car not found with id: " + id
                        ));

        return PremiumCarMapper.toDto(car);
    }

    @Override
    public List<PremiumCarDTO> getAllPremiumCars() {

        return premiumCarRepository.findAll()
                .stream()
                .map(PremiumCarMapper::toDto)
                .toList();
    }

    @Override
    public PremiumCarDTO updatePremiumCar(Long id, PremiumCarDTO dto) {

        PremiumCar existingCar = premiumCarRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                HttpStatus.NOT_FOUND,
                                "Premium car not found with id: " + id
                        ));

        //  Duplicate registration number (if changed)
        if (!existingCar.getRegistrationNumber().equals(dto.getRegistrationNumber())
                && premiumCarRepository.existsByRegistrationNumber(dto.getRegistrationNumber())) {

            throw new ApiException(
                    HttpStatus.CONFLICT,
                    "Registration number already exists"
            );
        }

        existingCar.setBrand(dto.getBrand());
        existingCar.setModel(dto.getModel());
        existingCar.setRegistrationNumber(dto.getRegistrationNumber());
        existingCar.setApproved(dto.getApproved());
        existingCar.setBooked(dto.getBooked());

        PremiumCar updatedCar = premiumCarRepository.save(existingCar);
        return PremiumCarMapper.toDto(updatedCar);
    }

    @Override
    public boolean deletePremiumCar(Long id) {

        Optional<PremiumCar> car = premiumCarRepository.findById(id);

        if (!car.empty().isPresent()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Car not found");
        }

        premiumCarRepository.delete(car.get());
        return true;
    }
}