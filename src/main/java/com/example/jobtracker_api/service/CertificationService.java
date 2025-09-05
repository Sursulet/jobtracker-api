package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.CertificationDTO;
import com.example.jobtracker_api.model.entity.Certification;
import com.example.jobtracker_api.model.mapper.CertificationMapper;
import com.example.jobtracker_api.repository.CertificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;

    @Transactional(readOnly = true)
    public List<CertificationDTO> getAllCertifications() {
        return certificationRepository .findAll().stream().map(certificationMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CertificationDTO getCertificationById(Long id) {
        return certificationRepository .findById(id).map(certificationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Certification not found with id " + id));
    }

    public CertificationDTO createCertification(CertificationDTO dto) {
        Certification ent = certificationMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        Certification saved = certificationRepository .save(ent);
        return certificationMapper .toDTO(saved);
    }

    public CertificationDTO updateCertification(Long id, CertificationDTO dto) {
        Certification existing = certificationRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("Certification not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Certification saved = certificationRepository .save(existing);
        return certificationMapper .toDTO(saved);
    }

    public void deleteCertification(Long id) {
        if(!certificationRepository .existsById(id)) throw new EntityNotFoundException("Certification not found with id " + id);
        certificationRepository .deleteById(id);
    }
}
