package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.InterviewDTO;
import com.example.jobtracker_api.service.InterviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;

    @GetMapping public ResponseEntity<List<InterviewDTO>> getAll() { return ResponseEntity.ok(interviewService.getAllInterviews()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<InterviewDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(interviewService.getInterviewById(id)); }
    @PostMapping public ResponseEntity<InterviewDTO> create(@Valid @RequestBody InterviewDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(interviewService.createInterview(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<InterviewDTO> update(@PathVariable Long id, @Valid @RequestBody InterviewDTO dto) { return ResponseEntity.ok(interviewService.updateInterview(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { interviewService.deleteInterview(id); return ResponseEntity.noContent().build(); }
}
