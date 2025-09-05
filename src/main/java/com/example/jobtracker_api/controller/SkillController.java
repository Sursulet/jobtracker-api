package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.SkillDTO;
import com.example.jobtracker_api.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping public ResponseEntity<List<SkillDTO>> getAll() { return ResponseEntity.ok(skillService.getAllSkills()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<SkillDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(skillService.getSkillById(id)); }
    @PostMapping public ResponseEntity<SkillDTO> create(@Valid @RequestBody SkillDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(skillService.createSkill(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<SkillDTO> update(@PathVariable Long id, @Valid @RequestBody SkillDTO dto) { return ResponseEntity.ok(skillService.updateSkill(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { skillService.deleteSkill(id); return ResponseEntity.noContent().build(); }
}
