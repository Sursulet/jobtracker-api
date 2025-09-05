package com.example.jobtracker_api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Contact extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String jobPosition;
    private String location;
    private String note;

    @OneToOne
    @JoinColumn(name = "contact_profile_id")
    private ContactProfile contactProfile;

    @OneToMany(mappedBy = "contact")
    private Set<Interview> interviews = new HashSet<>();
}

