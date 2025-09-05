package com.example.jobtracker_api.model.mapper;

import com.example.jobtracker_api.model.dto.ResumeDTO;
import com.example.jobtracker_api.model.entity.Resume;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeDTO toDTO(Resume resume);
    Resume toEntity(ResumeDTO dto);
}
