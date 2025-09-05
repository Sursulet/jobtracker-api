package com.example.jobtracker_api.model.mapper;

import com.example.jobtracker_api.model.dto.ContactDTO;
import com.example.jobtracker_api.model.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(source = "user.userId", target = "userId")
    ContactDTO toDTO(Contact contact);

    @Mapping(target = "user", ignore = true)
    Contact toEntity(ContactDTO dto);
}
