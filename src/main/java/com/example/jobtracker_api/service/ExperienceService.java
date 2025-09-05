package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ExperienceDTO;
import com.example.jobtracker_api.model.entity.Experience;
import com.example.jobtracker_api.model.mapper.ExperienceMapper;
import com.example.jobtracker_api.repository.ExperienceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    @Transactional(readOnly = true)
    public List<ExperienceDTO> getAllExperiences() {
        return experienceRepository .findAll().stream().map(experienceMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ExperienceDTO getExperienceById(Long id) {
        return experienceRepository .findById(id).map(experienceMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Experience not found with id " + id));
    }

    public ExperienceDTO createExperience(ExperienceDTO dto) {
        Experience ent = experienceMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Experience saved = experienceRepository .save(ent);
        return experienceMapper .toDTO(saved);
    }

    public ExperienceDTO updateExperience(Long id, ExperienceDTO dto) {
        Experience existing = experienceRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Experience not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Experience saved = experienceRepository .save(existing);
        return experienceMapper .toDTO(saved);
    }

    public void deleteExperience(Long id) {
        if(!experienceRepository .existsById(id)) throw new EntityNotFoundException("Experience not found with id " + id);
        experienceRepository .deleteById(id);
    }
}
