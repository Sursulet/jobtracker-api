package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.TaskDTO;
import com.example.jobtracker_api.model.entity.Task;
import com.example.jobtracker_api.model.mapper.TaskMapper;
import com.example.jobtracker_api.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<TaskDTO> getAllTasks() {
        return taskRepository .findAll().stream().map(taskMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long id) {
        return taskRepository .findById(id).map(taskMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));
    }

    public TaskDTO createTask(TaskDTO dto) {
        Task ent = taskMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Task saved = taskRepository .save(ent);
        return taskMapper .toDTO(saved);
    }

    public TaskDTO updateTask(Long id, TaskDTO dto) {
        Task existing = taskRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Task saved = taskRepository .save(existing);
        return taskMapper .toDTO(saved);
    }

    public void deleteTask(Long id) {
        if(!taskRepository .existsById(id)) throw new EntityNotFoundException("Task not found with id " + id);
        taskRepository .deleteById(id);
    }
}
