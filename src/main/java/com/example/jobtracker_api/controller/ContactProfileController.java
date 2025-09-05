package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ContactProfileDTO;
import com.example.jobtracker_api.service.ContactProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactprofiles")
@RequiredArgsConstructor
public class ContactProfileController {
    private final ContactProfileService contactProfileService;

    @GetMapping public ResponseEntity<List<ContactProfileDTO>> getAll() { return ResponseEntity.ok(contactProfileService.getAllContactProfiles()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ContactProfileDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(contactProfileService.getContactProfileById(id)); }
    @PostMapping public ResponseEntity<ContactProfileDTO> create(@Valid @RequestBody ContactProfileDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(contactProfileService.createContactProfile(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ContactProfileDTO> update(@PathVariable Long id, @Valid @RequestBody ContactProfileDTO dto) { return ResponseEntity.ok(contactProfileService.updateContactProfile(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { contactProfileService.deleteContactProfile(id); return ResponseEntity.noContent().build(); }
}
