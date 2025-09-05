package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.ProfessionalSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalSummaryRepository extends JpaRepository<ProfessionalSummary, Long> {
    // add custom queries here if needed, e.g. Optional<ProfessionalSummary> findByXxx(...);
}
