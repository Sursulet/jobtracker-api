package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.EducationDTO;
import com.example.jobtracker_api.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
@RequiredArgsConstructor
public class EducationController {
    private final EducationService educationService;

    @GetMapping public ResponseEntity<List<EducationDTO>> getAll() { return ResponseEntity.ok(educationService.getAllEducations()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<EducationDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(educationService.getEducationById(id)); }
    @PostMapping public ResponseEntity<EducationDTO> create(@Valid @RequestBody EducationDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(educationService.createEducation(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<EducationDTO> update(@PathVariable Long id, @Valid @RequestBody EducationDTO dto) { return ResponseEntity.ok(educationService.updateEducation(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { educationService.deleteEducation(id); return ResponseEntity.noContent().build(); }
}
