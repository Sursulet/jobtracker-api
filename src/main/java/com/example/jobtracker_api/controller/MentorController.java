package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.MentorDTO;
import com.example.jobtracker_api.service.MentorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    @GetMapping public ResponseEntity<List<MentorDTO>> getAll() { return ResponseEntity.ok(mentorService.getAllMentors()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<MentorDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(mentorService.getMentorById(id)); }
    @PostMapping public ResponseEntity<MentorDTO> create(@Valid @RequestBody MentorDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(mentorService.createMentor(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<MentorDTO> update(@PathVariable Long id, @Valid @RequestBody MentorDTO dto) { return ResponseEntity.ok(mentorService.updateMentor(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { mentorService.deleteMentor(id); return ResponseEntity.noContent().build(); }
}
