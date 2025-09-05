package com.example.jobtracker_api.model.mapper;

import com.example.jobtracker_api.model.dto.ApplicationDTO;
import com.example.jobtracker_api.model.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "offer.offerId", target = "offerId")
    @Mapping(source = "resume.resumeId", target = "resumeId")
    ApplicationDTO toDTO(Application application);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "offer", ignore = true)
    @Mapping(target = "resume", ignore = true)
    Application toEntity(ApplicationDTO dto);
}
