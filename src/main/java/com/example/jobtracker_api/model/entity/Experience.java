package com.example.jobtracker_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Experience extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long experienceId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String companyName;
    private String companyDescription;
    private String position;
    private String location;

    @Enumerated(EnumType.STRING)
    private ExperienceType type;

    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrently;

    @OneToMany(mappedBy = "experience")
    private Set<Task> tasks = new HashSet<>();
}
