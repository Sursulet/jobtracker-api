package com.example.jobtracker_api.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeDTO {
    private Long id;
    private Long userId;
}
