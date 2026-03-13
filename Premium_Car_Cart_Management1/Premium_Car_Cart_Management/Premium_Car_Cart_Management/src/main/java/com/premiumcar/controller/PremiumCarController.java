package com.premiumcar.controller;

import com.premiumcar.dto.PremiumCarDTO;
import com.premiumcar.exception.ApiResponse;
import com.premiumcar.service.PremiumCarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/premium-cars")
@RequiredArgsConstructor
public class PremiumCarController {

    private final PremiumCarService premiumCarService;

    // CREATE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PremiumCarDTO>> createPremiumCar(
            @Valid @RequestBody PremiumCarDTO dto) {

        PremiumCarDTO savedCar = premiumCarService.createPremiumCar(dto);

        ApiResponse<PremiumCarDTO> response =
                new ApiResponse<>("Premium car saved successfully", savedCar);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PremiumCarDTO> getPremiumCarById(@PathVariable Long id) {

        PremiumCarDTO car = premiumCarService.getPremiumCarById(id);

        if (car == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(car);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<PremiumCarDTO>> getAllPremiumCars() {

        List<PremiumCarDTO> cars = premiumCarService.getAllPremiumCars();
        return ResponseEntity.ok(cars);
    }

    // UPDATE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PremiumCarDTO> updatePremiumCar(
            @PathVariable Long id,
            @Valid @RequestBody PremiumCarDTO dto) {

        PremiumCarDTO updatedCar = premiumCarService.updatePremiumCar(id, dto);

        if (updatedCar == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedCar);
    }

    // DELETE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePremiumCar(@PathVariable Long id) {
        premiumCarService.deletePremiumCar(id);
        return ResponseEntity.ok("premium Car deleted successfully");

    }

}
