package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.GoalDTO;
import com.example.jobtracker_api.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @GetMapping public ResponseEntity<List<GoalDTO>> getAll() { return ResponseEntity.ok(goalService.getAllGoals()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<GoalDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(goalService.getGoalById(id)); }
    @PostMapping public ResponseEntity<GoalDTO> create(@Valid @RequestBody GoalDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(goalService.createGoal(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<GoalDTO> update(@PathVariable Long id, @Valid @RequestBody GoalDTO dto) { return ResponseEntity.ok(goalService.updateGoal(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { goalService.deleteGoal(id); return ResponseEntity.noContent().build(); }
}
