package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    // add custom queries here if needed, e.g. Optional<Skill> findByXxx(...);
}
