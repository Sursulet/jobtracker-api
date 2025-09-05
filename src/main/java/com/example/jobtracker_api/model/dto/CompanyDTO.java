package com.example.jobtracker_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long companyId;
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String location;
    private String note;
    private Integer rating;
}
