package com.healthconnect.role.service.impl;

import com.healthconnect.role.dto.request.CreateRoleRequest;
import com.healthconnect.role.dto.response.RoleResponse;
import com.healthconnect.role.entity.Role;
import com.healthconnect.role.mapper.RoleMapper;
import com.healthconnect.role.repository.RoleRepository;
import com.healthconnect.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(CreateRoleRequest request) {

        if (roleRepository.findByRoleName(request.getRoleName()).isPresent()) {
            throw new RuntimeException("Role already exists");
        }

        Role role = roleMapper.toEntity(request);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toResponse(savedRole);
    }

    @Override
    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponse getRoleByName(String roleName) {

        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        return roleMapper.toResponse(role);
    }
}