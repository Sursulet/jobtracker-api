package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ReminderDTO;
import com.example.jobtracker_api.service.ReminderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class ReminderController {
    private final ReminderService reminderService;

    @GetMapping public ResponseEntity<List<ReminderDTO>> getAll() { return ResponseEntity.ok(reminderService.getAllReminders()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ReminderDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(reminderService.getReminderById(id)); }
    @PostMapping public ResponseEntity<ReminderDTO> create(@Valid @RequestBody ReminderDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(reminderService.createReminder(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ReminderDTO> update(@PathVariable Long id, @Valid @RequestBody ReminderDTO dto) { return ResponseEntity.ok(reminderService.updateReminder(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { reminderService.deleteReminder(id); return ResponseEntity.noContent().build(); }
}
