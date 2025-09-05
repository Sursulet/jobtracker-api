package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.TargetTitleDTO;
import com.example.jobtracker_api.model.entity.TargetTitle;
import com.example.jobtracker_api.model.mapper.TargetTitleMapper;
import com.example.jobtracker_api.repository.TargetTitleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TargetTitleService {
    private final TargetTitleRepository targetTitleRepository;
    private final TargetTitleMapper targetTitleMapper;

    @Transactional(readOnly = true)
    public List<TargetTitleDTO> getAllTargetTitles() {
        return targetTitleRepository .findAll().stream().map(targetTitleMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public TargetTitleDTO getTargetTitleById(Long id) {
        return targetTitleRepository .findById(id).map(targetTitleMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("TargetTitle not found with id " + id));
    }

    public TargetTitleDTO createTargetTitle(TargetTitleDTO dto) {
        TargetTitle ent = targetTitleMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        TargetTitle saved = targetTitleRepository .save(ent);
        return targetTitleMapper .toDTO(saved);
    }

    public TargetTitleDTO updateTargetTitle(Long id, TargetTitleDTO dto) {
        TargetTitle existing = targetTitleRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("TargetTitle not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        TargetTitle saved = targetTitleRepository .save(existing);
        return targetTitleMapper .toDTO(saved);
    }

    public void deleteTargetTitle(Long id) {
        if(!targetTitleRepository .existsById(id)) throw new EntityNotFoundException("TargetTitle not found with id " + id);
        targetTitleRepository .deleteById(id);
    }
}
