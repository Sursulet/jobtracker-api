package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.ContactProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactProfileRepository extends JpaRepository<ContactProfile, Long> {
    // add custom queries here if needed, e.g. Optional<ContactProfile> findByXxx(...);
}
