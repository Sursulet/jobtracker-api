package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.exception.GlobalExceptionHandler;
import com.example.jobtracker_api.model.dto.ApplicationDTO;
import com.example.jobtracker_api.service.ApplicationService;
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
class ApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(applicationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAll() throws Exception {
        ApplicationDTO a1 = new ApplicationDTO(1L, null, null, null, null, null);
        ApplicationDTO a2 = new ApplicationDTO(2L, null, null, null, null, null);
        when(applicationService.getAllApplications()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(applicationService).getAllApplications();
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(applicationService.getApplicationById(1L)).thenThrow(new EntityNotFoundException("Application not found with id 1"));

        mockMvc.perform(get("/api/applications/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Application not found with id 1"));

        verify(applicationService).getApplicationById(1L);
    }

    @Test
    void testCreate() throws Exception {
        ApplicationDTO input = new ApplicationDTO(null, null, null, null, null, null);
        ApplicationDTO saved = new ApplicationDTO(5L, null, null, null, null, null);
        when(applicationService.createApplication(input)).thenReturn(saved);

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationId").value(5));
    }
}