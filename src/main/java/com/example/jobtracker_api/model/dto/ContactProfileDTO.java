package com.example.jobtracker_api.model.dto;

import com.example.jobtracker_api.model.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contact_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContactProfileDTO extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactProfileId;

    private String phone;
    private String linkedin;
    private String twitter;
    private String postalCode;
    private String city;
    private String website;

    @OneToOne(mappedBy = "contactProfile")
    private ContactDTO contact;
}
