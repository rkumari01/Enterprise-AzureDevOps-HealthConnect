package com.healthconnect.enrollment.repository;

import com.healthconnect.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByMemberId(String memberId);

    boolean existsByMemberId(String memberId);
}
