package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ResumeDTO;
import com.example.jobtracker_api.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping public ResponseEntity<List<ResumeDTO>> getAll() { return ResponseEntity.ok(resumeService.getAllResumes()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ResumeDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(resumeService.getResumeById(id)); }
    @PostMapping public ResponseEntity<ResumeDTO> create(@Valid @RequestBody ResumeDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(resumeService.createResume(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ResumeDTO> update(@PathVariable Long id, @Valid @RequestBody ResumeDTO dto) { return ResponseEntity.ok(resumeService.updateResume(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { resumeService.deleteResume(id); return ResponseEntity.noContent().build(); }
}
