package com.example.jobtracker_api.model.dto;

import com.example.jobtracker_api.model.entity.BasicEntity;
import com.example.jobtracker_api.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "certification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CertificationDTO extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String provider;
    private LocalDate startDate;
    private LocalDate endDate;
}
