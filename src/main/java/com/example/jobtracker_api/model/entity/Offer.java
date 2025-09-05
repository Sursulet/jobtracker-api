package com.example.jobtracker_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "offer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Offer extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String title;
    private String url;
    private LocalDate postedDate;
    private BigDecimal maxSalary;
    private BigDecimal minSalary;
    private String location;
    private Boolean isApplied = false;
    private LocalDate dateSaved;
    private LocalDate deadline;
    private LocalDate dateApplied;
    private Integer rating;
    private String description;
    private String note;

    @OneToMany(mappedBy = "offer")
    private Set<Application> applications = new HashSet<>();
}
