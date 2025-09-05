package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.NoteDTO;
import com.example.jobtracker_api.model.entity.Note;
import com.example.jobtracker_api.model.mapper.NoteMapper;
import com.example.jobtracker_api.repository.NoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Transactional(readOnly = true)
    public List<NoteDTO> getAllNotes() {
        return noteRepository .findAll().stream().map(noteMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public NoteDTO getNoteById(Long id) {
        return noteRepository .findById(id).map(noteMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + id));
    }

    public NoteDTO createNote(NoteDTO dto) {
        Note ent = noteMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Note saved = noteRepository .save(ent);
        return noteMapper .toDTO(saved);
    }

    public NoteDTO updateNote(Long id, NoteDTO dto) {
        Note existing = noteRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Note not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Note saved = noteRepository .save(existing);
        return noteMapper .toDTO(saved);
    }

    public void deleteNote(Long id) {
        if(!noteRepository .existsById(id)) throw new EntityNotFoundException("Note not found with id " + id);
        noteRepository .deleteById(id);
    }
}
