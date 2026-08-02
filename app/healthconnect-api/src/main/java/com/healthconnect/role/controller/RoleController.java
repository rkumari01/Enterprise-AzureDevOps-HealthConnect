package com.healthconnect.role.controller;

import com.healthconnect.role.dto.request.CreateRoleRequest;
import com.healthconnect.role.dto.response.RoleResponse;
import com.healthconnect.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse createRole(@Valid @RequestBody CreateRoleRequest request) {
        return roleService.createRole(request);
    }

    @GetMapping
    public List<RoleResponse> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{roleName}")
    public RoleResponse getRoleByName(@PathVariable String roleName) {
        return roleService.getRoleByName(roleName);
    }
}