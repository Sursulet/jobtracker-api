package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ApplicationDTO;
import com.example.jobtracker_api.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping public ResponseEntity<List<ApplicationDTO>> getAll() { return ResponseEntity.ok(applicationService.getAllApplications()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ApplicationDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(applicationService.getApplicationById(id)); }
    @PostMapping public ResponseEntity<ApplicationDTO> create(@Valid @RequestBody ApplicationDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.createApplication(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ApplicationDTO> update(@PathVariable Long id, @Valid @RequestBody ApplicationDTO dto) { return ResponseEntity.ok(applicationService.updateApplication(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { applicationService.deleteApplication(id); return ResponseEntity.noContent().build(); }
}
