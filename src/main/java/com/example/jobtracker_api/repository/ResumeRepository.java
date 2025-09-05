package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    // add custom queries here if needed, e.g. Optional<Resume> findByXxx(...);
}
