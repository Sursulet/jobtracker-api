package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    // add custom queries here if needed, e.g. Optional<Certification> findByXxx(...);
}
