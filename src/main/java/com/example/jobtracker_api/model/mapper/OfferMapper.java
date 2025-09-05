package com.example.jobtracker_api.model.mapper;

import com.example.jobtracker_api.model.dto.OfferDTO;
import com.example.jobtracker_api.model.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "company.companyId", target = "companyId")
    OfferDTO toDTO(Offer offer);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "company", ignore = true)
    Offer toEntity(OfferDTO dto);
}