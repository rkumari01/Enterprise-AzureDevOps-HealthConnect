package com.healthconnect.benefit.repository;

import com.healthconnect.benefit.entity.Benefit;
import com.healthconnect.employer.entity.Employer;
import com.healthconnect.employer.repository.EmployerRepository;
import com.healthconnect.enrollment.entity.Enrollment;
import com.healthconnect.enrollment.repository.EnrollmentRepository;
import com.healthconnect.role.entity.Role;
import com.healthconnect.role.repository.RoleRepository;
import com.healthconnect.user.entity.User;
import com.healthconnect.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BenefitRepositoryTest {

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Should find benefit by benefit code")
    void shouldFindBenefitByCode() {

        String unique = String.valueOf(System.nanoTime());

        // Create Role
        Role role = new Role();
        role.setRoleName("ROLE_" + unique);
        role.setDescription("Test Role");
        role = roleRepository.save(role);

        // Create User
        User user = User.builder()
                .employeeId("EMP-" + unique)
                .firstName("John")
                .lastName("Doe")
                .email("john" + unique + "@test.com")
                .password("password")
                .role(role)
                .build();

        user = userRepository.save(user);

        // Create Employer
        Employer employer = Employer.builder()
                .employerCode("EMPLOYER-" + unique)
                .companyName("ABC Company")
                .contactPerson("Manager")
                .contactEmail("manager" + unique + "@test.com")
                .phoneNumber("9999999999")
                .status("ACTIVE")
                .build();

        employer = employerRepository.save(employer);

        // Create Enrollment
        Enrollment enrollment = Enrollment.builder()
                .memberId("MEM-" + unique)
                .planName("Gold Plan")
                .coverageType("Family")
                .effectiveDate(LocalDate.now())
                .status("ACTIVE")
                .user(user)
                .employer(employer)
                .build();

        enrollment = enrollmentRepository.save(enrollment);

        // Create Benefit
        Benefit benefit = new Benefit();
        benefit.setBenefitCode("BEN-" + unique);
        benefit.setBenefitName("Medical");
        benefit.setDescription("Medical Coverage");
        benefit.setCoverageAmount(new BigDecimal("100000"));
        benefit.setIsActive(true);
        benefit.setEnrollment(enrollment);

        benefitRepository.save(benefit);

        assertThat(benefitRepository.findByBenefitCode("BEN-" + unique))
                .isPresent();

        assertThat(benefitRepository.existsByBenefitCode("BEN-" + unique))
                .isTrue();
    }

    @Test
    @DisplayName("Should return empty when benefit not found")
    void shouldReturnEmptyWhenBenefitNotFound() {

        assertThat(benefitRepository.findByBenefitCode("UNKNOWN"))
                .isEmpty();

        assertThat(benefitRepository.existsByBenefitCode("UNKNOWN"))
                .isFalse();
    }
}