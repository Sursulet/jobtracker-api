package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.RoleDTO;
import com.example.jobtracker_api.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping public ResponseEntity<List<RoleDTO>> getAll() { return ResponseEntity.ok(roleService.getAllRoles()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<RoleDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(roleService.getRoleById(id)); }
    @PostMapping public ResponseEntity<RoleDTO> create(@Valid @RequestBody RoleDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<RoleDTO> update(@PathVariable Long id, @Valid @RequestBody RoleDTO dto) { return ResponseEntity.ok(roleService.updateRole(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { roleService.deleteRole(id); return ResponseEntity.noContent().build(); }
}
