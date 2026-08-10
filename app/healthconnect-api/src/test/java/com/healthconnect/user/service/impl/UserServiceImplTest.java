package com.healthconnect.user.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.role.entity.Role;
import com.healthconnect.role.repository.RoleRepository;
import com.healthconnect.user.dto.request.CreateUserRequest;
import com.healthconnect.user.dto.response.UserResponse;
import com.healthconnect.user.entity.User;
import com.healthconnect.user.mapper.UserMapper;
import com.healthconnect.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_ShouldCreateUserSuccessfully() {

        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        request.setEmployeeId("EMP001");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@test.com");
        request.setPassword("password");
        request.setRoleId(1L);

        Role role = new Role();

        User user = User.builder()
                .employeeId("EMP001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@test.com")
                .password("password")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .employeeId("EMP001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@test.com")
                .password("password")
                .role(role)
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .employeeId("EMP001")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@test.com")
                .roleName("ADMIN")
                .build();

        when(userMapper.toEntity(request)).thenReturn(user);

        when(roleRepository.findById(1L))
                .thenReturn(Optional.of(role));

        when(userRepository.save(user))
                .thenReturn(savedUser);

        when(userMapper.toResponse(savedUser))
                .thenReturn(response);

        // Act
        UserResponse result = userService.createUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("EMP001", result.getEmployeeId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john.doe@test.com", result.getEmail());

        verify(userMapper, times(1)).toEntity(request);
        verify(roleRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).toResponse(savedUser);

        verify(auditService, times(1))
                .logEvent(
                        eq("USER_CREATED"),
                        eq("User"),
                        eq(1L),
                        contains("john.doe@test.com"),
                        eq("SYSTEM")
                );

        verifyNoMoreInteractions(
                userRepository,
                roleRepository,
                userMapper,
                auditService
        );
    }

    @Test
    void createUser_ShouldThrowException_WhenRoleNotFound() {

        CreateUserRequest request = new CreateUserRequest();
        request.setRoleId(100L);

        User user = new User();

        when(userMapper.toEntity(request))
                .thenReturn(user);

        when(roleRepository.findById(100L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> userService.createUser(request));

        assertEquals("Role not found", exception.getMessage());

        verify(userMapper).toEntity(request);
        verify(roleRepository).findById(100L);

        verify(userRepository, never()).save(any());

        verify(auditService, never()).logEvent(
                anyString(),
                anyString(),
                anyLong(),
                anyString(),
                anyString()
        );
    }
}