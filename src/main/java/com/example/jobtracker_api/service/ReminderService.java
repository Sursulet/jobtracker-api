package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ReminderDTO;
import com.example.jobtracker_api.model.entity.Reminder;
import com.example.jobtracker_api.model.mapper.ReminderMapper;
import com.example.jobtracker_api.repository.ReminderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;

    @Transactional(readOnly = true)
    public List<ReminderDTO> getAllReminders() {
        return reminderRepository .findAll().stream().map(reminderMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ReminderDTO getReminderById(Long id) {
        return reminderRepository .findById(id).map(reminderMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found with id " + id));
    }

    public ReminderDTO createReminder(ReminderDTO dto) {
        Reminder ent = reminderMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Reminder saved = reminderRepository .save(ent);
        return reminderMapper .toDTO(saved);
    }

    public ReminderDTO updateReminder(Long id, ReminderDTO dto) {
        Reminder existing = reminderRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Reminder not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Reminder saved = reminderRepository .save(existing);
        return reminderMapper .toDTO(saved);
    }

    public void deleteReminder(Long id) {
        if(!reminderRepository .existsById(id)) throw new EntityNotFoundException("Reminder not found with id " + id);
        reminderRepository .deleteById(id);
    }
}
