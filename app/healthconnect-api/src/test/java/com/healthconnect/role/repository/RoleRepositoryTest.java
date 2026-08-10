package com.healthconnect.role.repository;

import com.healthconnect.role.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Should find role by role name")
    void shouldFindRoleByRoleName() {

        String unique = String.valueOf(System.nanoTime());

        Role role = new Role();
        role.setRoleName("ADMIN_" + unique);
        role.setDescription("Administrator");

        roleRepository.save(role);

        assertThat(roleRepository.findByRoleName("ADMIN_" + unique))
                .isPresent();
    }

    @Test
    @DisplayName("Should return empty when role not found")
    void shouldReturnEmptyWhenRoleNotFound() {

        assertThat(roleRepository.findByRoleName("UNKNOWN_ROLE"))
                .isEmpty();
    }
}