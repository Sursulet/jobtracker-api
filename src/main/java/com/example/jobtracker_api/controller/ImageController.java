package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.model.dto.ImageDTO;
import com.example.jobtracker_api.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping public ResponseEntity<List<ImageDTO>> getAll() { return ResponseEntity.ok(imageService.getAllImages()); }
    @GetMapping("/<built-in function id>") public ResponseEntity<ImageDTO> getById(@PathVariable Long id) { return ResponseEntity.ok(imageService.getImageById(id)); }
    @PostMapping public ResponseEntity<ImageDTO> create(@Valid @RequestBody ImageDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(imageService.createImage(dto)); }
    @PutMapping("/<built-in function id>") public ResponseEntity<ImageDTO> update(@PathVariable Long id, @Valid @RequestBody ImageDTO dto) { return ResponseEntity.ok(imageService.updateImage(id,dto)); }
    @DeleteMapping("/<built-in function id>") public ResponseEntity<Void> delete(@PathVariable Long id) { imageService.deleteImage(id); return ResponseEntity.noContent().build(); }
}
