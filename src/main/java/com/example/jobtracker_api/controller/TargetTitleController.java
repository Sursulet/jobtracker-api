package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.TargetTitleDTO;
import com.example.jobtracker_api.service.TargetTitleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/targettitles")
@RequiredArgsConstructor
public class TargetTitleController {
    private final TargetTitleService targetTitleService;

    @GetMapping public ResponseEntity<List<TargetTitleDTO>> getAll() { return ResponseEntity.ok(targetTitleService.getAllTargetTitles()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<TargetTitleDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(targetTitleService.getTargetTitleById(id)); }
    @PostMapping public ResponseEntity<TargetTitleDTO> create(@Valid @RequestBody TargetTitleDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(targetTitleService.createTargetTitle(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<TargetTitleDTO> update(@PathVariable Long id, @Valid @RequestBody TargetTitleDTO dto) { return ResponseEntity.ok(targetTitleService.updateTargetTitle(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { targetTitleService.deleteTargetTitle(id); return ResponseEntity.noContent().build(); }
}
