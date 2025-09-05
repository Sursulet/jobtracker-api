package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.RoleDTO;
import com.example.jobtracker_api.model.entity.Role;
import com.example.jobtracker_api.model.mapper.RoleMapper;
import com.example.jobtracker_api.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoles() {
        return roleRepository .findAll().stream().map(roleMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public RoleDTO getRoleById(Long id) {
        return roleRepository .findById(id).map(roleMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
    }

    public RoleDTO createRole(RoleDTO dto) {
        Role ent = roleMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Role saved = roleRepository .save(ent);
        return roleMapper .toDTO(saved);
    }

    public RoleDTO updateRole(Long id, RoleDTO dto) {
        Role existing = roleRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Role saved = roleRepository .save(existing);
        return roleMapper .toDTO(saved);
    }

    public void deleteRole(Long id) {
        if(!roleRepository .existsById(id)) throw new EntityNotFoundException("Role not found with id " + id);
        roleRepository .deleteById(id);
    }
}
