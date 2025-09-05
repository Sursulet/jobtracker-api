package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ResumeDTO;
import com.example.jobtracker_api.model.entity.Resume;
import com.example.jobtracker_api.model.mapper.ResumeMapper;
import com.example.jobtracker_api.repository.ResumeRepository;
import com.example.jobtracker_api.repository.UserRepository;
import com.example.jobtracker_api.repository.ContactProfileRepository;
import com.example.jobtracker_api.repository.TargetTitleRepository;
import com.example.jobtracker_api.repository.ProfessionalSummaryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;
    private final UserRepository userRepository;
    private final ContactProfileRepository contactProfileRepository;
    private final TargetTitleRepository targetTitleRepository;
    private final ProfessionalSummaryRepository professionalSummaryRepository;

    @Transactional(readOnly = true)
    public List<ResumeDTO> getAllResumes() {
        return resumeRepository.findAll().stream().map(resumeMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ResumeDTO getResumeById(Long id) {
        return resumeRepository.findById(id).map(resumeMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Resume not found with id " + id));
    }

    public ResumeDTO createResume(ResumeDTO dto) {
        Resume ent = resumeMapper.toEntity(dto);
        if (dto.getUserId() != null) ent.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId())));
        if (dto.getContactProfileId() != null) ent.setContactProfile(contactProfileRepository.findById(dto.getContactProfileId()).orElseThrow(() -> new EntityNotFoundException("ContactProfile not found with id " + dto.getContactProfileId())));
        if (dto.getTargetTitleId() != null) ent.setTargetTitle(targetTitleRepository.findById(dto.getTargetTitleId()).orElseThrow(() -> new EntityNotFoundException("TargetTitle not found with id " + dto.getTargetTitleId())));
        if (dto.getProfessionalSummaryId() != null) ent.setProfessionalSummary(professionalSummaryRepository.findById(dto.getProfessionalSummaryId()).orElseThrow(() -> new EntityNotFoundException("ProfessionalSummary not found with id " + dto.getProfessionalSummaryId())));
        // TODO: resolve lists (experienceIds, educationIds, etc.)
        ent.setCreatedAt(LocalDateTime.now());
        Resume saved = resumeRepository.save(ent);
        return resumeMapper.toDTO(saved);
    }

    public ResumeDTO updateResume(Long id, ResumeDTO dto) {
        Resume existing = resumeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resume not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        Resume saved = resumeRepository.save(existing);
        return resumeMapper.toDTO(saved);
    }

    public void deleteResume(Long id) {
        if (!resumeRepository.existsById(id)) throw new EntityNotFoundException("Resume not found with id " + id);
        resumeRepository.deleteById(id);
    }
}
