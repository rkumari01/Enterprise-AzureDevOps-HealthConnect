package com.healthconnect.benefit.repository;

import com.healthconnect.benefit.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    Optional<Benefit> findByBenefitCode(String benefitCode);

    boolean existsByBenefitCode(String benefitCode);
}