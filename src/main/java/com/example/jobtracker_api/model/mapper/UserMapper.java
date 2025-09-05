package com.example.jobtracker_api.model.mapper;

import com.example.jobtracker_api.model.dto.UserDTO;
import com.example.jobtracker_api.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
}
