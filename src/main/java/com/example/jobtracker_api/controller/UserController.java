package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.UserDTO;
import com.example.jobtracker_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping public ResponseEntity<List<UserDTO>> getAll() { return ResponseEntity.ok(userService.getAllUsers()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<UserDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(userService.getUserById(id)); }
    @PostMapping public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) { return ResponseEntity.ok(userService.updateUser(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { userService.deleteUser(id); return ResponseEntity.noContent().build(); }
}
