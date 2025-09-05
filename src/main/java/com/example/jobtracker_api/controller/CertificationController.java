package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.CertificationDTO;
import com.example.jobtracker_api.service.CertificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @GetMapping public ResponseEntity<List<CertificationDTO>> getAll() { return ResponseEntity.ok(certificationService.getAllCertifications()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<CertificationDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(certificationService.getCertificationById(id)); }
    @PostMapping public ResponseEntity<CertificationDTO> create(@Valid @RequestBody CertificationDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(certificationService.createCertification(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<CertificationDTO> update(@PathVariable Long id, @Valid @RequestBody CertificationDTO dto) { return ResponseEntity.ok(certificationService.updateCertification(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { certificationService.deleteCertification(id); return ResponseEntity.noContent().build(); }
}
