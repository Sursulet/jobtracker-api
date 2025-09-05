package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.GoalDTO;
import com.example.jobtracker_api.model.entity.Goal;
import com.example.jobtracker_api.model.mapper.GoalMapper;
import com.example.jobtracker_api.repository.GoalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {
    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;

    @Transactional(readOnly = true)
    public List<GoalDTO> getAllGoals() {
        return goalRepository .findAll().stream().map(goalMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public GoalDTO getGoalById(Long id) {
        return goalRepository .findById(id).map(goalMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id " + id));
    }

    public GoalDTO createGoal(GoalDTO dto) {
        Goal ent = goalMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Goal saved = goalRepository .save(ent);
        return goalMapper .toDTO(saved);
    }

    public GoalDTO updateGoal(Long id, GoalDTO dto) {
        Goal existing = goalRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Goal not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Goal saved = goalRepository .save(existing);
        return goalMapper .toDTO(saved);
    }

    public void deleteGoal(Long id) {
        if(!goalRepository .existsById(id)) throw new EntityNotFoundException("Goal not found with id " + id);
        goalRepository .deleteById(id);
    }
}
