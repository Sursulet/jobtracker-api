package com.example.jobtracker_api.model.mapper;

import com.example.jobtracker_api.model.dto.MentorDTO;
import com.example.jobtracker_api.model.entity.Mentor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MentorMapper {

    @Mapping(source = "user.userId", target = "userId")
    MentorDTO toDTO(Mentor mentor);

    @Mapping(target = "user", ignore = true)
    Mentor toEntity(MentorDTO dto);
}