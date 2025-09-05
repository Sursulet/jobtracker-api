package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.CategoryDTO;
import com.example.jobtracker_api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorys")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping public ResponseEntity<List<CategoryDTO>> getAll() { return ResponseEntity.ok(categoryService.getAllCategorys()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(categoryService.getCategoryById(id)); }
    @PostMapping public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) { return ResponseEntity.ok(categoryService.updateCategory(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { categoryService.deleteCategory(id); return ResponseEntity.noContent().build(); }
}
