package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ImageDTO;
import com.example.jobtracker_api.model.entity.Image;
import com.example.jobtracker_api.model.mapper.ImageMapper;
import com.example.jobtracker_api.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Transactional(readOnly = true)
    public List<ImageDTO> getAllImages() {
        return imageRepository .findAll().stream().map(imageMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ImageDTO getImageById(Long id) {
        return imageRepository .findById(id).map(imageMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Image not found with id " + id));
    }

    public ImageDTO createImage(ImageDTO dto) {
        Image ent = imageMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Image saved = imageRepository .save(ent);
        return imageMapper .toDTO(saved);
    }

    public ImageDTO updateImage(Long id, ImageDTO dto) {
        Image existing = imageRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Image not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Image saved = imageRepository .save(existing);
        return imageMapper .toDTO(saved);
    }

    public void deleteImage(Long id) {
        if(!imageRepository .existsById(id)) throw new EntityNotFoundException("Image not found with id " + id);
        imageRepository .deleteById(id);
    }
}
