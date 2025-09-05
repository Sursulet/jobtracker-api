package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.SkillDTO;
import com.example.jobtracker_api.model.entity.Skill;
import com.example.jobtracker_api.model.mapper.SkillMapper;
import com.example.jobtracker_api.repository.SkillRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Transactional(readOnly = true)
    public List<SkillDTO> getAllSkills() {
        return skillRepository .findAll().stream().map(skillMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public SkillDTO getSkillById(Long id) {
        return skillRepository .findById(id).map(skillMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found with id " + id));
    }

    public SkillDTO createSkill(SkillDTO dto) {
        Skill ent = skillMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Skill saved = skillRepository .save(ent);
        return skillMapper .toDTO(saved);
    }

    public SkillDTO updateSkill(Long id, SkillDTO dto) {
        Skill existing = skillRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Skill not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Skill saved = skillRepository .save(existing);
        return skillMapper .toDTO(saved);
    }

    public void deleteSkill(Long id) {
        if(!skillRepository .existsById(id)) throw new EntityNotFoundException("Skill not found with id " + id);
        skillRepository .deleteById(id);
    }
}
