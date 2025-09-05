package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.NoteDTO;
import com.example.jobtracker_api.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping public ResponseEntity<List<NoteDTO>> getAll() { return ResponseEntity.ok(noteService.getAllNotes()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<NoteDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(noteService.getNoteById(id)); }
    @PostMapping public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<NoteDTO> update(@PathVariable Long id, @Valid @RequestBody NoteDTO dto) { return ResponseEntity.ok(noteService.updateNote(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { noteService.deleteNote(id); return ResponseEntity.noContent().build(); }
}
