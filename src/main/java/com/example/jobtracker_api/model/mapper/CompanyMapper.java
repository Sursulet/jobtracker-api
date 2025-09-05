package com.example.jobtracker_api.model.mapper;

import com.example.jobtracker_api.model.dto.CompanyDTO;
import com.example.jobtracker_api.model.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(source = "user.userId", target = "userId")
    CompanyDTO toDTO(Company company);

    @Mapping(target = "user", ignore = true)
    Company toEntity(CompanyDTO dto);
}
