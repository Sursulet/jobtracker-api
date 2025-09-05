package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.exception.GlobalExceptionHandler;
import com.example.jobtracker_api.model.dto.ContactDTO;
import com.example.jobtracker_api.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(contactController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAll() throws Exception {
        ContactDTO c1 = new ContactDTO(1L, null, "A", "B", "a@b", "111", null, null, null);
        ContactDTO c2 = new ContactDTO(2L, null, "C", "D", "c@d", "222", null, null, null);
        when(contactService.getAllContacts()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(contactService.getContactById(1L)).thenThrow(new EntityNotFoundException("Contact not found with id 1"));

        mockMvc.perform(get("/api/contacts/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Contact not found with id 1"));
    }
}