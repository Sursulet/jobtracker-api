package com.example.jobtracker_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Long applicationId;
    private Long userId;
    private Long offerId;
    private String type;
    private String status;
    private Long resumeId;
}