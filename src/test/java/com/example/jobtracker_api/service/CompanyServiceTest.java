package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.CompanyDTO;
import com.example.jobtracker_api.model.entity.Company;
import com.example.jobtracker_api.model.mapper.CompanyMapper;
import com.example.jobtracker_api.repository.CompanyRepository;
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
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void testGetAllCompanies() {
        Company c1 = new Company(); c1.setCompanyId(1L);
        Company c2 = new Company(); c2.setCompanyId(2L);
        CompanyDTO d1 = new CompanyDTO(1L, null, "N1", "e1", "p1", "loc1", "note1", 5);
        CompanyDTO d2 = new CompanyDTO(2L, null, "N2", "e2", "p2", "loc2", "note2", 4);

        when(companyRepository.findAll()).thenReturn(List.of(c1, c2));
        when(companyMapper.toDTO(c1)).thenReturn(d1);
        when(companyMapper.toDTO(c2)).thenReturn(d2);

        var res = companyService.getAllCompanies();
        assertEquals(2, res.size());
    }

    @Test
    void testGetCompanyById_NotFound() {
        when(companyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> companyService.getCompanyById(1L));
    }

    @Test
    void testCreateCompany() {
        CompanyDTO dto = new CompanyDTO(null, null, "N", "e", "p", "loc", "note", 3);
        Company ent = new Company();
        Company saved = new Company(); saved.setCompanyId(99L);
        CompanyDTO savedDto = new CompanyDTO(99L, null, "N", "e", "p", "loc", "note", 3);

        when(companyMapper.toEntity(dto)).thenReturn(ent);
        when(companyRepository.save(ent)).thenReturn(saved);
        when(companyMapper.toDTO(saved)).thenReturn(savedDto);

        CompanyDTO res = companyService.createCompany(dto);
        assertEquals(99L, res.getCompanyId());
    }
}
