package com.example.jobtracker_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorDTO {
    private Long mentorId;
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}