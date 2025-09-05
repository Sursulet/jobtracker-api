package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ExperienceDTO;
import com.example.jobtracker_api.service.ExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
public class ExperienceController {
    private final ExperienceService experienceService;

    @GetMapping public ResponseEntity<List<ExperienceDTO>> getAll() { return ResponseEntity.ok(experienceService.getAllExperiences()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ExperienceDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(experienceService.getExperienceById(id)); }
    @PostMapping public ResponseEntity<ExperienceDTO> create(@Valid @RequestBody ExperienceDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(experienceService.createExperience(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ExperienceDTO> update(@PathVariable Long id, @Valid @RequestBody ExperienceDTO dto) { return ResponseEntity.ok(experienceService.updateExperience(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { experienceService.deleteExperience(id); return ResponseEntity.noContent().build(); }
}
