package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    // add custom queries here if needed, e.g. Optional<Experience> findByXxx(...);
}
