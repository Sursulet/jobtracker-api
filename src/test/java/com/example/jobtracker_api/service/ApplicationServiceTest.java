package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.ApplicationDTO;
import com.example.jobtracker_api.model.entity.Application;
import com.example.jobtracker_api.model.mapper.ApplicationMapper;
import com.example.jobtracker_api.repository.ApplicationRepository;
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
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void testGetAllApplications() {
        Application a1 = new Application(); a1.setApplicationId(1L);
        Application a2 = new Application(); a2.setApplicationId(2L);
        ApplicationDTO d1 = new ApplicationDTO(1L, null, null, null, null, null);
        ApplicationDTO d2 = new ApplicationDTO(2L, null, null, null, null, null);

        when(applicationRepository.findAll()).thenReturn(List.of(a1, a2));
        when(applicationMapper.toDTO(a1)).thenReturn(d1);
        when(applicationMapper.toDTO(a2)).thenReturn(d2);

        var res = applicationService.getAllApplications();
        assertEquals(2, res.size());
    }

    @Test
    void testGetApplicationById_Found() {
        Application a = new Application(); a.setApplicationId(1L);
        ApplicationDTO dto = new ApplicationDTO(1L, null, null, null, null, null);

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(a));
        when(applicationMapper.toDTO(a)).thenReturn(dto);

        ApplicationDTO res = applicationService.getApplicationById(1L);
        assertEquals(1L, res.getApplicationId());
    }

    @Test
    void testGetApplicationById_NotFound() {
        when(applicationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> applicationService.getApplicationById(1L));
    }

    @Test
    void testCreateApplication() {
        ApplicationDTO dto = new ApplicationDTO(null, null, null, null, null, null);
        Application ent = new Application();
        Application saved = new Application(); saved.setApplicationId(5L);
        ApplicationDTO savedDto = new ApplicationDTO(5L, null, null, null, null, null);

        when(applicationMapper.toEntity(dto)).thenReturn(ent);
        when(applicationRepository.save(ent)).thenReturn(saved);
        when(applicationMapper.toDTO(saved)).thenReturn(savedDto);

        ApplicationDTO res = applicationService.createApplication(dto);
        assertEquals(5L, res.getApplicationId());
    }

    @Test
    void testUpdateApplication_Found() {
        Application existing = new Application(); existing.setApplicationId(1L);
        ApplicationDTO dto = new ApplicationDTO(null, null, null, null, null, null);
        Application updated = new Application(); updated.setApplicationId(1L);
        ApplicationDTO updatedDto = new ApplicationDTO(1L, null, null, null, null, null);

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(applicationRepository.save(existing)).thenReturn(updated);
        when(applicationMapper.toDTO(updated)).thenReturn(updatedDto);

        ApplicationDTO res = applicationService.updateApplication(1L, dto);
        assertEquals(1L, res.getApplicationId());
    }

    @Test
    void testDeleteApplication_NotFound() {
        when(applicationRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> applicationService.deleteApplication(1L));
    }
}