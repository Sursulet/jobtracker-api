package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ApplicationDTO;
import com.example.jobtracker_api.model.entity.Application;
import com.example.jobtracker_api.model.mapper.ApplicationMapper;
import com.example.jobtracker_api.repository.ApplicationRepository;
import com.example.jobtracker_api.repository.OfferRepository;
import com.example.jobtracker_api.repository.UserRepository;
import com.example.jobtracker_api.repository.ResumeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final ResumeRepository resumeRepository;

    @Transactional(readOnly = true)
    public List<ApplicationDTO> getAllApplications() {
        return applicationRepository.findAll().stream().map(applicationMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ApplicationDTO getApplicationById(Long id) {
        return applicationRepository.findById(id).map(applicationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id " + id));
    }

    public ApplicationDTO createApplication(ApplicationDTO dto) {
        Application ent = applicationMapper.toEntity(dto);
        if (dto.getUserId() != null) ent.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId())));
        if (dto.getOfferId() != null) ent.setOffer(offerRepository.findById(dto.getOfferId()).orElseThrow(() -> new EntityNotFoundException("Offer not found with id " + dto.getOfferId())));
        if (dto.getResumeId() != null) ent.setResume(resumeRepository.findById(dto.getResumeId()).orElseThrow(() -> new EntityNotFoundException("Resume not found with id " + dto.getResumeId())));
        ent.setCreatedAt(LocalDateTime.now());
        Application saved = applicationRepository.save(ent);
        return applicationMapper.toDTO(saved);
    }

    public ApplicationDTO updateApplication(Long id, ApplicationDTO dto) {
        Application existing = applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application not found with id " + id));
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus().name());
        if (dto.getType() != null) existing.setType(dto.getType().name());
        existing.setUpdatedAt(LocalDateTime.now());
        Application saved = applicationRepository.save(existing);
        return applicationMapper.toDTO(saved);
    }

    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) throw new EntityNotFoundException("Application not found with id " + id);
        application_repository.deleteById(id);
    }
}
