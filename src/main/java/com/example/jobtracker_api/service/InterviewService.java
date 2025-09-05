package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.InterviewDTO;
import com.example.jobtracker_api.model.entity.Interview;
import com.example.jobtracker_api.model.mapper.InterviewMapper;
import com.example.jobtracker_api.repository.InterviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewMapper interviewMapper;

    @Transactional(readOnly = true)
    public List<InterviewDTO> getAllInterviews() {
        return interviewRepository .findAll().stream().map(interviewMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public InterviewDTO getInterviewById(Long id) {
        return interviewRepository .findById(id).map(interviewMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Interview not found with id " + id));
    }

    public InterviewDTO createInterview(InterviewDTO dto) {
        Interview ent = interviewMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Interview saved = interviewRepository .save(ent);
        return interviewMapper .toDTO(saved);
    }

    public InterviewDTO updateInterview(Long id, InterviewDTO dto) {
        Interview existing = interviewRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Interview not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Interview saved = interviewRepository .save(existing);
        return interviewMapper .toDTO(saved);
    }

    public void deleteInterview(Long id) {
        if(!interviewRepository .existsById(id)) throw new EntityNotFoundException("Interview not found with id " + id);
        interviewRepository .deleteById(id);
    }
}
