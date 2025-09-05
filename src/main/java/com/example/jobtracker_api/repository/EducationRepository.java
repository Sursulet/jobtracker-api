package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    // add custom queries here if needed, e.g. Optional<Education> findByXxx(...);
}
