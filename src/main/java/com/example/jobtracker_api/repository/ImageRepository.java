package com.example.jobtracker_api.repository;

import com.example.jobtracker_api.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    // add custom queries here if needed, e.g. Optional<Image> findByXxx(...);
}
