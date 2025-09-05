package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.UserDTO;
import com.example.jobtracker_api.model.entity.User;
import com.example.jobtracker_api.model.mapper.UserMapper;
import com.example.jobtracker_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository .findAll().stream().map(userMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository .findById(id).map(userMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

    public UserDTO createUser(UserDTO dto) {
        User ent = userMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        User saved = userRepository .save(ent);
        return userMapper .toDTO(saved);
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        User existing = userRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        User saved = userRepository .save(existing);
        return userMapper .toDTO(saved);
    }

    public void deleteUser(Long id) {
        if(!userRepository .existsById(id)) throw new EntityNotFoundException("User not found with id " + id);
        userRepository .deleteById(id);
    }
}
