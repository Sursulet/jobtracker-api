package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    // add custom queries here if needed, e.g. Optional<Interview> findByXxx(...);
}
