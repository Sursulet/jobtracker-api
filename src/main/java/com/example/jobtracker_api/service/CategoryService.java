package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.CategoryDTO;
import com.example.jobtracker_api.model.entity.Category;
import com.example.jobtracker_api.model.mapper.CategoryMapper;
import com.example.jobtracker_api.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategorys() {
        return categoryRepository .findAll().stream().map(categoryMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository .findById(id).map(categoryMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        Category ent = categoryMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Category saved = categoryRepository .save(ent);
        return categoryMapper .toDTO(saved);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category existing = categoryRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Category saved = categoryRepository .save(existing);
        return categoryMapper .toDTO(saved);
    }

    public void deleteCategory(Long id) {
        if(!categoryRepository .existsById(id)) throw new EntityNotFoundException("Category not found with id " + id);
        categoryRepository .deleteById(id);
    }
}
