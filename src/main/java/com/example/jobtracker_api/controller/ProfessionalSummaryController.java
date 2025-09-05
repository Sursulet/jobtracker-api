package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ProfessionalSummaryDTO;
import com.example.jobtracker_api.service.ProfessionalSummaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professionalsummarys")
@RequiredArgsConstructor
public class ProfessionalSummaryController {
    private final ProfessionalSummaryService professionalSummaryService;

    @GetMapping public ResponseEntity<List<ProfessionalSummaryDTO>> getAll() { return ResponseEntity.ok(professionalSummaryService.getAllProfessionalSummarys()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ProfessionalSummaryDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(professionalSummaryService.getProfessionalSummaryById(id)); }
    @PostMapping public ResponseEntity<ProfessionalSummaryDTO> create(@Valid @RequestBody ProfessionalSummaryDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(professionalSummaryService.createProfessionalSummary(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ProfessionalSummaryDTO> update(@PathVariable Long id, @Valid @RequestBody ProfessionalSummaryDTO dto) { return ResponseEntity.ok(professionalSummaryService.updateProfessionalSummary(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { professionalSummaryService.deleteProfessionalSummary(id); return ResponseEntity.noContent().build(); }
}
