package com.healthconnect.employer.repository;

import com.healthconnect.employer.entity.Employer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployerRepositoryTest {

    @Autowired
    private EmployerRepository employerRepository;

    @Test
    @DisplayName("Should find employer by employer code")
    void shouldFindEmployerByCode() {

        String unique = String.valueOf(System.nanoTime());

        Employer employer = Employer.builder()
                .employerCode("EMP_" + unique)
                .companyName("ABC Corp")
                .contactPerson("John Doe")
                .contactEmail("john_" + unique + "@test.com")
                .phoneNumber("9999999999")
                .status("ACTIVE")
                .build();

        employerRepository.save(employer);

        assertThat(employerRepository.findByEmployerCode("EMP_" + unique))
                .isPresent();

        assertThat(employerRepository.existsByEmployerCode("EMP_" + unique))
                .isTrue();

        assertThat(employerRepository.findByContactEmail("john_" + unique + "@test.com"))
                .isPresent();

        assertThat(employerRepository.existsByContactEmail("john_" + unique + "@test.com"))
                .isTrue();
    }

    @Test
    @DisplayName("Should return empty for unknown employer")
    void shouldReturnEmptyWhenEmployerNotFound() {

        assertThat(employerRepository.findByEmployerCode("UNKNOWN"))
                .isEmpty();
    }
}