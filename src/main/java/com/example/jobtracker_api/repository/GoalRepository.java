package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    // add custom queries here if needed, e.g. Optional<Goal> findByXxx(...);
}
