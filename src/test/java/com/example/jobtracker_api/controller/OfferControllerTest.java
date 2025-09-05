package com.example.jobtracker_api.controller;

import com.example.jobtracker_api.exception.GlobalExceptionHandler;
import com.example.jobtracker_api.model.dto.OfferDTO;
import com.example.jobtracker_api.service.OfferService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OfferControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OfferService offerService;

    @InjectMocks
    private OfferController offerController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(offerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetAllOffers() throws Exception {
        OfferDTO o1 = new OfferDTO(1L, 1L, 1L, "T1", null, LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, "loc", false, null, null, null, 5, "d", null);
        OfferDTO o2 = new OfferDTO(2L, 2L, 2L, "T2", null, LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, "loc", false, null, null, null, 4, "d2", null);

        when(offerService.getAllOffers()).thenReturn(List.of(o1, o2));

        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("T1"));

        verify(offerService).getAllOffers();
    }

    @Test
    void testGetOfferById_Found() throws Exception {
        OfferDTO dto = new OfferDTO(1L, 1L, 1L, "T", null, LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, "loc", false, null, null, null, 5, "d", null);
        when(offerService.getOfferById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/offers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("T"));

        verify(offerService).getOfferById(1L);
    }

    @Test
    void testGetOfferById_NotFound() throws Exception {
        when(offerService.getOfferById(1L)).thenThrow(new EntityNotFoundException("Offer not found with id 1"));

        mockMvc.perform(get("/api/offers/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Offer not found with id 1"));

        verify(offerService).getOfferById(1L);
    }

    @Test
    void testCreateOffer() throws Exception {
        OfferDTO input = new OfferDTO(null, 1L, 1L, "New", null, null, null, null, null, false, null, null, null, null, null, null);
        OfferDTO saved = new OfferDTO(10L, 1L, 1L, "New", null, null, null, null, null, false, null, null, null, null, null, null);

        when(offerService.createOffer(input)).thenReturn(saved);

        mockMvc.perform(post("/api/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.offerId").value(10));

        verify(offerService).createOffer(input);
    }

    @Test
    void testUpdateOffer_Found() throws Exception {
        OfferDTO input = new OfferDTO(null, null, null, "Updated", null, null, null, null, null, null, null, null, null, null, null, null);
        OfferDTO updated = new OfferDTO(1L, null, null, "Updated", null, null, null, null, null, null, null, null, null, null, null, null);

        when(offerService.updateOffer(1L, input)).thenReturn(updated);

        mockMvc.perform(put("/api/offers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));

        verify(offerService).updateOffer(1L, input);
    }

    @Test
    void testDeleteOffer_Found() throws Exception {
        doNothing().when(offerService).deleteOffer(1L);

        mockMvc.perform(delete("/api/offers/1"))
                .andExpect(status().isNoContent());

        verify(offerService).deleteOffer(1L);
    }
}
