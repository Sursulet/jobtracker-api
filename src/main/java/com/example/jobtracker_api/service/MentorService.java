package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.MentorDTO;
import com.example.jobtracker_api.model.entity.Mentor;
import com.example.jobtracker_api.model.mapper.MentorMapper;
import com.example.jobtracker_api.repository.MentorRepository;
import com.example.jobtracker_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorService {
    private final MentorRepository mentorRepository;
    private final MentorMapper mentorMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<MentorDTO> getAllMentors() {
        return mentorRepository.findAll().stream().map(mentorMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public MentorDTO getMentorById(Long id) {
        return mentorRepository.findById(id).map(mentorMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found with id " + id));
    }

    public MentorDTO createMentor(MentorDTO dto) {
        Mentor ent = mentorMapper.toEntity(dto);
        if (dto.getUserId() != null) ent.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId())));
        ent.setCreatedAt(LocalDateTime.now());
        Mentor saved = mentorRepository.save(ent);
        return mentorMapper.toDTO(saved);
    }

    public MentorDTO updateMentor(Long id, MentorDTO dto) {
        Mentor existing = mentorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mentor not found with id " + id));
        if (dto.getFirstname() != null) existing.setFirstname(dto.getFirstname());
        existing.setUpdatedAt(LocalDateTime.now());
        Mentor saved = mentorRepository.save(existing);
        return mentorMapper.toDTO(saved);
    }

    public void deleteMentor(Long id) {
        if (!mentorRepository.existsById(id)) throw new EntityNotFoundException("Mentor not found with id " + id);
        mentorRepository.deleteById(id);
    }
}
