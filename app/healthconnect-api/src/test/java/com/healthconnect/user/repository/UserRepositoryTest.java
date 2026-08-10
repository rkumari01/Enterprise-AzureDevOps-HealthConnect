package com.healthconnect.user.repository;

import com.healthconnect.role.entity.Role;
import com.healthconnect.role.repository.RoleRepository;
import com.healthconnect.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {

        String unique = String.valueOf(System.nanoTime());

        Role role = new Role();
        role.setRoleName("ADMIN_" + unique);
        role.setDescription("Administrator");

        role = roleRepository.save(role);

        User user = User.builder()
                .employeeId("EMP_" + unique)
                .firstName("John")
                .lastName("Doe")
                .email("john_" + unique + "@test.com")
                .password("password")
                .role(role)
                .build();

        userRepository.save(user);

        assertThat(userRepository.findByEmail("john_" + unique + "@test.com"))
                .isPresent();
    }

    @Test
    @DisplayName("Should return empty when email not found")
    void shouldReturnEmptyWhenEmailNotFound() {

        assertThat(userRepository.findByEmail("unknown@test.com"))
                .isEmpty();
    }

    @Test
    @DisplayName("Should check existing employeeId")
    void shouldCheckEmployeeIdExists() {

        String unique = String.valueOf(System.nanoTime());

        Role role = new Role();
        role.setRoleName("USER_" + unique);
        role.setDescription("User");

        role = roleRepository.save(role);

        User user = User.builder()
                .employeeId("EMP_" + unique)
                .firstName("Alex")
                .lastName("Smith")
                .email("alex_" + unique + "@test.com")
                .password("password")
                .role(role)
                .build();

        userRepository.save(user);

        assertThat(userRepository.existsByEmployeeId("EMP_" + unique))
                .isTrue();
    }

    @Test
    @DisplayName("Should check email exists")
    void shouldCheckEmailExists() {

        String unique = String.valueOf(System.nanoTime());

        Role role = new Role();
        role.setRoleName("HR_" + unique);
        role.setDescription("Human Resource");

        role = roleRepository.save(role);

        User user = User.builder()
                .employeeId("EMP_" + unique)
                .firstName("Mary")
                .lastName("Jane")
                .email("mary_" + unique + "@test.com")
                .password("password")
                .role(role)
                .build();

        userRepository.save(user);

        assertThat(userRepository.existsByEmail("mary_" + unique + "@test.com"))
                .isTrue();
    }
}