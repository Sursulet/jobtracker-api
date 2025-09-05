package com.example.jobtracker_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long contactId;
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String jobPosition;
    private String location;
    private String note;
}
