package com.example.jobtracker_api.service;

import com.example.jobtracker_api.model.dto.OfferDTO;
import com.example.jobtracker_api.model.entity.Offer;
import com.example.jobtracker_api.model.mapper.OfferMapper;
import com.example.jobtracker_api.repository.CompanyRepository;
import com.example.jobtracker_api.repository.OfferRepository;
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
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll().stream().map(offerMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public OfferDTO getOfferById(Long id) {
        return offerRepository.findById(id).map(offerMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Offer not found with id " + id));
    }

    public OfferDTO createOffer(OfferDTO dto) {
        Offer ent = offerMapper.toEntity(dto);
        if (dto.getUserId() != null) {
            ent.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId())));
        }
        if (dto.getCompanyId() != null) {
            ent.setCompany(companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId())));
        }
        ent.setCreatedAt(LocalDateTime.now());
        Offer saved = offerRepository.save(ent);
        return offerMapper.toDTO(saved);
    }

    public OfferDTO updateOffer(Long id, OfferDTO dto) {
        Offer existing = offerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Offer not found with id " + id));
        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getRating() != null) existing.setRating(dto.getRating());
        if (dto.getUserId() != null) existing.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found with id " + dto.getUserId())));
        if (dto.getCompanyId() != null) existing.setCompany(companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new EntityNotFoundException("Company not found with id " + dto.getCompanyId())));
        existing.setUpdatedAt(LocalDateTime.now());
        Offer saved = offerRepository.save(existing);
        return offerMapper.toDTO(saved);
    }

    public void deleteOffer(Long id) {
        if (!offerRepository.existsById(id)) throw new EntityNotFoundException("Offer not found with id " + id);
        offerRepository.deleteById(id);
    }
}
