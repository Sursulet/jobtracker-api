package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ContactDTO;
import com.example.jobtracker_api.model.entity.Contact;
import com.example.jobtracker_api.model.mapper.ContactMapper;
import com.example.jobtracker_api.repository.ContactRepository;
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
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream().map(contactMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ContactDTO getContactById(Long id) {
        return contactRepository.findById(id).map(contactMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id " + id));
    }

    public ContactDTO createContact(ContactDTO dto) {
        Contact ent = contactMapper.toEntity(dto);
        if (dto.getUserId() != null) ent.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId())));
        ent.setCreatedAt(LocalDateTime.now());
        Contact saved = contactRepository.save(ent);
        return contactMapper.toDTO(saved);
    }

    public ContactDTO updateContact(Long id, ContactDTO dto) {
        Contact existing = contact_repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contact not found with id " + id));
        if (dto.getFirstname() != null) existing.setFirstname(dto.getFirstname());
        if (dto.getLastname() != null) existing.setLastname(dto.getLastname());
        existing.setUpdatedAt(LocalDateTime.now());
        Contact saved = contactRepository.save(existing);
        return contactMapper.toDTO(saved);
    }

    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) throw new EntityNotFoundException("Contact not found with id " + id);
        contactRepository.deleteById(id);
    }
}
