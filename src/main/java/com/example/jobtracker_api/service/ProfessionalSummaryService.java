package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ProfessionalSummaryDTO;
import com.example.jobtracker_api.model.entity.ProfessionalSummary;
import com.example.jobtracker_api.model.mapper.ProfessionalSummaryMapper;
import com.example.jobtracker_api.repository.ProfessionalSummaryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfessionalSummaryService {
    private final ProfessionalSummaryRepository professionalSummaryRepository;
    private final ProfessionalSummaryMapper professionalSummaryMapper;

    @Transactional(readOnly = true)
    public List<ProfessionalSummaryDTO> getAllProfessionalSummarys() {
        return professionalSummaryRepository .findAll().stream().map(professionalSummaryMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ProfessionalSummaryDTO getProfessionalSummaryById(Long id) {
        return professionalSummaryRepository .findById(id).map(professionalSummaryMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("ProfessionalSummary not found with id " + id));
    }

    public ProfessionalSummaryDTO createProfessionalSummary(ProfessionalSummaryDTO dto) {
        ProfessionalSummary ent = professionalSummaryMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        ProfessionalSummary saved = professionalSummaryRepository .save(ent);
        return professionalSummaryMapper .toDTO(saved);
    }

    public ProfessionalSummaryDTO updateProfessionalSummary(Long id, ProfessionalSummaryDTO dto) {
        ProfessionalSummary existing = professionalSummaryRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("ProfessionalSummary not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        ProfessionalSummary saved = professionalSummaryRepository .save(existing);
        return professionalSummaryMapper .toDTO(saved);
    }

    public void deleteProfessionalSummary(Long id) {
        if(!professionalSummaryRepository .existsById(id)) throw new EntityNotFoundException("ProfessionalSummary not found with id " + id);
        professionalSummaryRepository .deleteById(id);
    }
}
