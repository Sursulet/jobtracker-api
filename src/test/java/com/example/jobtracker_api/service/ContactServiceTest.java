package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ContactDTO;
import com.example.jobtracker_api.model.entity.Contact;
import com.example.jobtracker_api.model.mapper.ContactMapper;
import com.example.jobtracker_api.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactService contactService;

    @Test
    void testGetAllContacts() {
        Contact c1 = new Contact(); c1.setContactId(1L);
        Contact c2 = new Contact(); c2.setContactId(2L);
        ContactDTO d1 = new ContactDTO(1L, null, "A", "B", "a@b", "111", null, null, null);
        ContactDTO d2 = new ContactDTO(2L, null, "C", "D", "c@d", "222", null, null, null);

        when(contactRepository.findAll()).thenReturn(List.of(c1, c2));
        when(contactMapper.toDTO(c1)).thenReturn(d1);
        when(contactMapper.toDTO(c2)).thenReturn(d2);

        var res = contactService.getAllContacts();
        assertEquals(2, res.size());
    }

    @Test
    void testGetContactById_NotFound() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> contactService.getContactById(1L));
    }

    @Test
    void testCreateContact() {
        ContactDTO dto = new ContactDTO(null, null, "A", "B", "a@b", "111", null, null, null);
        Contact ent = new Contact();
        Contact saved = new Contact(); saved.setContactId(10L);
        ContactDTO savedDto = new ContactDTO(10L, null, "A", "B", "a@b", "111", null, null, null);

        when(contactMapper.toEntity(dto)).thenReturn(ent);
        when(contactRepository.save(ent)).thenReturn(saved);
        when(contactMapper.toDTO(saved)).thenReturn(savedDto);

        ContactDTO res = contactService.createContact(dto);
        assertEquals(10L, res.getContactId());
    }
}
