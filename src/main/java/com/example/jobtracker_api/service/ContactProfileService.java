package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ContactProfileDTO;
import com.example.jobtracker_api.model.entity.ContactProfile;
import com.example.jobtracker_api.model.mapper.ContactProfileMapper;
import com.example.jobtracker_api.repository.ContactProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactProfileService {
    private final ContactProfileRepository contactProfileRepository;
    private final ContactProfileMapper contactProfileMapper;

    @Transactional(readOnly = true)
    public List<ContactProfileDTO> getAllContactProfiles() {
        return contactProfileRepository .findAll().stream().map(contactProfileMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ContactProfileDTO getContactProfileById(Long id) {
        return contactProfileRepository .findById(id).map(contactProfileMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("ContactProfile not found with id " + id));
    }

    public ContactProfileDTO createContactProfile(ContactProfileDTO dto) {
        ContactProfile ent = contactProfileMapper .toEntity(dto);
        ent.setCreatedAt(LocalDateTime.now());
        ContactProfile saved = contactProfileRepository .save(ent);
        return contactProfileMapper .toDTO(saved);
    }

    public ContactProfileDTO updateContactProfile(Long id, ContactProfileDTO dto) {
        ContactProfile existing = contactProfileRepository .findById(id).orElseThrow(() -> new EntityNotFoundException("ContactProfile not found with id " + id));
        existing.setUpdatedAt(LocalDateTime.now());
        ContactProfile saved = contactProfileRepository .save(existing);
        return contactProfileMapper .toDTO(saved);
    }

    public void deleteContactProfile(Long id) {
        if(!contactProfileRepository .existsById(id)) throw new EntityNotFoundException("ContactProfile not found with id " + id);
        contactProfileRepository .deleteById(id);
    }
}
