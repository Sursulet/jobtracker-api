package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.TaskDTO;
import com.example.jobtracker_api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping public ResponseEntity<List<TaskDTO>> getAll() { return ResponseEntity.ok(taskService.getAllTasks()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<TaskDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(taskService.getTaskById(id)); }
    @PostMapping public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<TaskDTO> update(@PathVariable Long id, @Valid @RequestBody TaskDTO dto) { return ResponseEntity.ok(taskService.updateTask(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { taskService.deleteTask(id); return ResponseEntity.noContent().build(); }
}
