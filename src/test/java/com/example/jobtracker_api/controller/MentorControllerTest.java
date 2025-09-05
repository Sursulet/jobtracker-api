package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.exception.GlobalExceptionHandler;
import com.example.jobtracker_api.model.dto.MentorDTO;
import com.example.jobtracker_api.service.MentorService;
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
class MentorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MentorService mentorService;

    @InjectMocks
    private MentorController mentorController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(mentorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAll() throws Exception {
        MentorDTO m1 = new MentorDTO(1L, null, "A", "B", "a@b", "123");
        MentorDTO m2 = new MentorDTO(2L, null, "C", "D", "c@d", "456");
        when(mentorService.getAllMentors()).thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/api/mentors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(mentorService).getAllMentors();
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(mentorService.getMentorById(1L)).thenThrow(new EntityNotFoundException("Mentor not found with id 1"));

        mockMvc.perform(get("/api/mentors/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Mentor not found with id 1"));

        verify(mentorService).getMentorById(1L);
    }

    @Test
    void testCreate() throws Exception {
        MentorDTO input = new MentorDTO(null, null, "A", "B", "a@b", "123");
        MentorDTO saved = new MentorDTO(5L, null, "A", "B", "a@b", "123");
        when(mentorService.createMentor(input)).thenReturn(saved);

        mockMvc.perform(post("/api/mentors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mentorId").value(5));
    }
}
