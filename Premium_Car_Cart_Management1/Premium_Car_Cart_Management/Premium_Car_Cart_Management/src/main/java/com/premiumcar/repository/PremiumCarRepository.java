package com.premiumcar.repository;

import com.premiumcar.entity.PremiumCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumCarRepository extends JpaRepository<PremiumCar, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);
}

