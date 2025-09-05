package com.example.jobtracker_api.model.dto;

import com.example.jobtracker_api.model.entity.BasicEntity;
import com.example.jobtracker_api.model.entity.Skill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryDTO extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String label;

    @OneToMany(mappedBy = "category")
    private Set<Skill> skills = new HashSet<>();
}
