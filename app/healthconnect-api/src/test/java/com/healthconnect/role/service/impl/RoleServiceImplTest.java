package com.healthconnect.role.service.impl;

import com.healthconnect.role.dto.request.CreateRoleRequest;
import com.healthconnect.role.dto.response.RoleResponse;
import com.healthconnect.role.entity.Role;
import com.healthconnect.role.mapper.RoleMapper;
import com.healthconnect.role.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void createRole_ShouldCreateRoleSuccessfully() {

        CreateRoleRequest request = new CreateRoleRequest();
        request.setRoleName("ADMIN");
        request.setDescription("Administrator");

        Role role = new Role();
        role.setRoleName("ADMIN");
        role.setDescription("Administrator");

        Role savedRole = new Role();
        savedRole.setRoleName("ADMIN");
        savedRole.setDescription("Administrator");

        RoleResponse response = new RoleResponse();
        response.setId(1L);
        response.setRoleName("ADMIN");
        response.setDescription("Administrator");
        response.setCreatedAt(LocalDateTime.now());

        when(roleRepository.findByRoleName("ADMIN"))
                .thenReturn(Optional.empty());

        when(roleMapper.toEntity(request))
                .thenReturn(role);

        when(roleRepository.save(role))
                .thenReturn(savedRole);

        when(roleMapper.toResponse(savedRole))
                .thenReturn(response);

        RoleResponse result = roleService.createRole(request);

        assertNotNull(result);
        assertEquals("ADMIN", result.getRoleName());
        assertEquals("Administrator", result.getDescription());

        verify(roleRepository).findByRoleName("ADMIN");
        verify(roleMapper).toEntity(request);
        verify(roleRepository).save(role);
        verify(roleMapper).toResponse(savedRole);
    }

    @Test
    void createRole_ShouldThrowException_WhenRoleAlreadyExists() {

        CreateRoleRequest request = new CreateRoleRequest();
        request.setRoleName("ADMIN");

        Role role = new Role();

        when(roleRepository.findByRoleName("ADMIN"))
                .thenReturn(Optional.of(role));

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> roleService.createRole(request));

        assertEquals("Role already exists", exception.getMessage());

        verify(roleRepository).findByRoleName("ADMIN");
        verify(roleRepository, never()).save(any());
    }

    @Test
    void getAllRoles_ShouldReturnAllRoles() {

        Role role1 = new Role();
        role1.setRoleName("ADMIN");
        role1.setDescription("Administrator");

        Role role2 = new Role();
        role2.setRoleName("USER");
        role2.setDescription("Normal User");

        RoleResponse response1 = new RoleResponse();
        response1.setRoleName("ADMIN");

        RoleResponse response2 = new RoleResponse();
        response2.setRoleName("USER");

        when(roleRepository.findAll())
                .thenReturn(List.of(role1, role2));

        when(roleMapper.toResponse(role1))
                .thenReturn(response1);

        when(roleMapper.toResponse(role2))
                .thenReturn(response2);

        List<RoleResponse> result = roleService.getAllRoles();

        assertEquals(2, result.size());

        verify(roleRepository).findAll();
    }

    @Test
    void getRoleByName_ShouldReturnRole() {

        Role role = new Role();
        role.setRoleName("ADMIN");

        RoleResponse response = new RoleResponse();
        response.setRoleName("ADMIN");

        when(roleRepository.findByRoleName("ADMIN"))
                .thenReturn(Optional.of(role));

        when(roleMapper.toResponse(role))
                .thenReturn(response);

        RoleResponse result = roleService.getRoleByName("ADMIN");

        assertNotNull(result);
        assertEquals("ADMIN", result.getRoleName());

        verify(roleRepository).findByRoleName("ADMIN");
        verify(roleMapper).toResponse(role);
    }

    @Test
    void getRoleByName_ShouldThrowException_WhenRoleNotFound() {

        when(roleRepository.findByRoleName("ADMIN"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> roleService.getRoleByName("ADMIN"));

        assertEquals("Role not found", exception.getMessage());

        verify(roleRepository).findByRoleName("ADMIN");
    }
}