package com.healthconnect.employer.repository;

import com.healthconnect.employer.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Optional<Employer> findByEmployerCode(String employerCode);

    Optional<Employer> findByContactEmail(String contactEmail);

    boolean existsByEmployerCode(String employerCode);

    boolean existsByContactEmail(String contactEmail);
}
