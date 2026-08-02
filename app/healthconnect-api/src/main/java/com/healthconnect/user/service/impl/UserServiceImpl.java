package com.healthconnect.user.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.role.entity.Role;
import com.healthconnect.role.repository.RoleRepository;
import com.healthconnect.user.dto.request.CreateUserRequest;
import com.healthconnect.user.dto.response.UserResponse;
import com.healthconnect.user.entity.User;
import com.healthconnect.user.mapper.UserMapper;
import com.healthconnect.user.repository.UserRepository;
import com.healthconnect.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuditService auditService;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        User user = userMapper.toEntity(request);

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRole(role);

        User savedUser = userRepository.save(user);
        auditService.logEvent(
        "USER_CREATED",
        "User",
        savedUser.getId(),
        "User " + savedUser.getEmail() + " created.",
        "SYSTEM");

        return userMapper.toResponse(savedUser);
    }
}
