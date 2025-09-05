package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.exception.GlobalExceptionHandler;
import com.example.jobtracker_api.model.dto.CompanyDTO;
import com.example.jobtracker_api.service.CompanyService;
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
class CompanyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(companyController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAll() throws Exception {
        CompanyDTO c1 = new CompanyDTO(1L, null, "N1", "e1", "p1", "loc1", "note1", 5);
        CompanyDTO c2 = new CompanyDTO(2L, null, "N2", "e2", "p2", "loc2", "note2", 4);
        when(companyService.getAllCompanies()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(companyService.getCompanyById(1L)).thenThrow(new EntityNotFoundException("Company not found with id 1"));

        mockMvc.perform(get("/api/companies/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Company not found with id 1"));
    }

    @Test
    void testCreate() throws Exception {
        CompanyDTO input = new CompanyDTO(null, null, "N", "e", "p", "loc", "note", 3);
        CompanyDTO saved = new CompanyDTO(5L, null, "N", "e", "p", "loc", "note", 3);
        when(companyService.createCompany(input)).thenReturn(saved);

        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").value(5));
    }
}
