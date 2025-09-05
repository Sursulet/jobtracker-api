package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.OfferDTO;
import com.example.jobtracker_api.model.entity.Offer;
import com.example.jobtracker_api.model.mapper.OfferMapper;
import com.example.jobtracker_api.repository.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferMapper offerMapper;

    @InjectMocks
    private OfferService offerService;

    @Test
    void testGetAllOffers() {
        Offer o1 = new Offer(); o1.setOfferId(1L); o1.setTitle("T1");
        Offer o2 = new Offer(); o2.setOfferId(2L); o2.setTitle("T2");
        OfferDTO d1 = new OfferDTO(1L, null, null, "T1", null, null, null, null, null, null, null, null, null, null, null, null);
        OfferDTO d2 = new OfferDTO(2L, null, null, "T2", null, null, null, null, null, null, null, null, null, null, null, null);

        when(offerRepository.findAll()).thenReturn(List.of(o1, o2));
        when(offerMapper.toDTO(o1)).thenReturn(d1);
        when(offerMapper.toDTO(o2)).thenReturn(d2);

        var result = offerService.getAllOffers();

        assertEquals(2, result.size());
        assertEquals("T1", result.get(0).getTitle());
        verify(offerRepository, times(1)).findAll();
    }

    @Test
    void testGetOfferById_Found() {
        Offer o = new Offer(); o.setOfferId(1L); o.setTitle("T");
        OfferDTO dto = new OfferDTO(1L, null, null, "T", null, LocalDate.now(), BigDecimal.ONE, BigDecimal.ZERO, "loc", false, null, null, null, 5, "desc", null);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(o));
        when(offerMapper.toDTO(o)).thenReturn(dto);

        OfferDTO res = offerService.getOfferById(1L);

        assertNotNull(res);
        assertEquals("T", res.getTitle());
        verify(offerRepository).findById(1L);
    }

    @Test
    void testGetOfferById_NotFound() {
        when(offerRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> offerService.getOfferById(1L));
        assertTrue(ex.getMessage().contains("Offer not found"));
    }

    @Test
    void testCreateOffer() {
        OfferDTO dto = new OfferDTO(null, null, null, "New", null, null, null, null, null, null, null, null, null, null, null, null);
        Offer entity = new Offer(); entity.setTitle("New");
        Offer saved = new Offer(); saved.setOfferId(10L); saved.setTitle("New");
        OfferDTO savedDto = new OfferDTO(10L, null, null, "New", null, null, null, null, null, null, null, null, null, null, null, null);

        when(offerMapper.toEntity(dto)).thenReturn(entity);
        when(offerRepository.save(entity)).thenReturn(saved);
        when(offerMapper.toDTO(saved)).thenReturn(savedDto);

        OfferDTO res = offerService.createOffer(dto);

        assertNotNull(res);
        assertEquals(10L, res.getOfferId());
        verify(offerRepository).save(entity);
    }

    @Test
    void testUpdateOffer_Found() {
        Offer existing = new Offer(); existing.setOfferId(1L); existing.setTitle("Old");
        Offer updatedEntity = new Offer(); updatedEntity.setOfferId(1L); updatedEntity.setTitle("New");
        OfferDTO dto = new OfferDTO(null, null, null, "New", null, null, null, null, null, null, null, null, null, null, null, null);
        OfferDTO updatedDto = new OfferDTO(1L, null, null, "New", null, null, null, null, null, null, null, null, null, null, null, null);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(offerRepository.save(existing)).thenReturn(updatedEntity);
        when(offerMapper.toDTO(updatedEntity)).thenReturn(updatedDto);

        OfferDTO res = offerService.updateOffer(1L, dto);

        assertEquals("New", res.getTitle());
        verify(offerRepository).findById(1L);
        verify(offerRepository).save(existing);
    }

    @Test
    void testDeleteOffer() {
        when(offerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(offerRepository).deleteById(1L);

        assertDoesNotThrow(() -> offerService.deleteOffer(1L));
        verify(offerRepository).deleteById(1L);
    }

    @Test
    void testDeleteOffer_NotFound() {
        when(offerRepository.existsById(1L)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> offerService.deleteOffer(1L));
        assertTrue(ex.getMessage().contains("Offer not found"));
    }
}