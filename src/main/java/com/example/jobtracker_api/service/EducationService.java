package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.EducationDTO;
import com.example.jobtracker_api.model.entity.Education;
import com.example.jobtracker_api.model.mapper.EducationMapper;
import com.example.jobtracker_api.repository.EducationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationService {
    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;

    @Transactional(readOnly = true)
    public List<EducationDTO> getAllEducations() {
        return educationRepository .findAll().stream().map(educationMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public EducationDTO getEducationById(Long id) {
        return educationRepository .findById(id).map(educationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with id " + id));
    }

    public EducationDTO createEducation(EducationDTO dto) {
        Education ent = educationMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Education saved = educationRepository .save(ent);
        return educationMapper .toDTO(saved);
    }

    public EducationDTO updateEducation(Long id, EducationDTO dto) {
        Education existing = educationRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Education not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Education saved = educationRepository .save(existing);
        return educationMapper .toDTO(saved);
    }

    public void deleteEducation(Long id) {
        if(!educationRepository .existsById(id)) throw new EntityNotFoundException("Education not found with id " + id);
        educationRepository .deleteById(id);
    }
}
