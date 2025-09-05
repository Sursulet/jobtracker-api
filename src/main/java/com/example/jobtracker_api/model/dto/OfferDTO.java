package com.example.jobtracker_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    private Long offerId;
    private Long userId;
    private Long companyId;
    private String title;
    private String url;
    private LocalDate postedDate;
    private BigDecimal maxSalary;
    private BigDecimal minSalary;
    private String location;
    private Boolean isApplied;
    private LocalDate dateSaved;
    private LocalDate deadline;
    private LocalDate dateApplied;
    private Integer rating;
    private String description;
    private String note;
}
