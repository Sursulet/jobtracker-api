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
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Company extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String email;
    private String phone;
    private String location;
    private String note;
    private Integer rating;

    @OneToMany(mappedBy = "company")
    private Set<Offer> offers = new HashSet<>();
}
