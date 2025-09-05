package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.CompanyDTO;
import com.example.jobtracker_api.model.entity.Company;
import com.example.jobtracker_api.model.mapper.CompanyMapper;
import com.example.jobtracker_api.repository.CompanyRepository;
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
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream().map(companyMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CompanyDTO getCompanyById(Long id) {
        return companyRepository.findById(id).map(companyMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id " + id));
    }

    public CompanyDTO createCompany(CompanyDTO dto) {
        Company ent = companyMapper.toEntity(dto);
        if (dto.getUserId() != null) ent.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId())));
        ent.setCreatedAt(LocalDateTime.now());
        Company saved = companyRepository.save(ent);
        return companyMapper.toDTO(saved);
    }

    public CompanyDTO updateCompany(Long id, CompanyDTO dto) {
        Company existing = companyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Company not found with id " + id));
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getRating() != null) existing.setRating(dto.getRating());
        existing.setUpdatedAt(LocalDateTime.now());
        Company saved = companyRepository.save(existing);
        return companyMapper.toDTO(saved);
    }

    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) throw new EntityNotFoundException("Company not found with id " + id);
        companyRepository.deleteById(id);
    }
}
