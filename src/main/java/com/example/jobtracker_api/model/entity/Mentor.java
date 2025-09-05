package com.example.jobtracker_api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "mentor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Mentor extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}

