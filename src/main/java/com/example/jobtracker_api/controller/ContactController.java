package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ContactDTO;
import com.example.jobtracker_api.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping public ResponseEntity<List<ContactDTO>> getAll() { return ResponseEntity.ok(contactService.getAllContacts()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ContactDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(contactService.getContactById(id)); }
    @PostMapping public ResponseEntity<ContactDTO> create(@Valid @RequestBody ContactDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createContact(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ContactDTO> update(@PathVariable Long id, @Valid @RequestBody ContactDTO dto) { return ResponseEntity.ok(contactService.updateContact(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { contactService.deleteContact(id); return ResponseEntity.noContent().build(); }
}
